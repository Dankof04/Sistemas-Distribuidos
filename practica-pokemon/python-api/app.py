from flask import Flask, jsonify
import requests

app = Flask(__name__)

POKEAPI_URL = "https://pokeapi.co/api/v2/pokemon/"

def get_basic_info(name_or_id):
    try:
        r = requests.get(f"{POKEAPI_URL}{str(name_or_id).lower()}", timeout=3)
        if r.status_code != 200: return None
        data = r.json()
        return {
            "name": data['name'],
            "spriteUrl": data['sprites']['front_default'],
            "types": [t['type']['name'] for t in data['types']]
        }
    except: return None

@app.route('/api/pokemon/<name>', methods=['GET'])
def get_pokemon(name):
    try:
        res = requests.get(f"{POKEAPI_URL}{name.lower().strip()}", timeout=5)
        if res.status_code == 404:
            return jsonify({"error": "No encontrado"}), 404

        main_data = res.json()
        species_url = main_data['species']['url']
        spec_res = requests.get(species_url).json()

        # 1. Variedades Regionales
        varieties = []
        for var in spec_res['varieties']:
            v_name = var['pokemon']['name']
            if v_name != main_data['name']:
                v_info = get_basic_info(v_name)
                if v_info: varieties.append(v_info)

        # 2. AGRUPACIÓN REAL DE ENCUENTROS
        enc_res = requests.get(main_data['location_area_encounters']).json()
        grouped_encounters = {}

        for e in enc_res:
            # Limpiamos el nombre de la ruta
            loc_name = e['location_area']['name'].replace('-', ' ').title()

            # Creamos la clave del juego (ej: "Diamond / Pearl / Platinum")
            versions = sorted([v['version']['name'].replace('-', ' ').capitalize() for v in e['version_details']])
            game_key = " / ".join(versions)

            # Si el juego ya está, añadimos la ruta a su lista. Si no, creamos la lista.
            if game_key not in grouped_encounters:
                grouped_encounters[game_key] = []

            if loc_name not in grouped_encounters[game_key]:
                grouped_encounters[game_key].append(loc_name)

        # Convertimos el diccionario en la lista que espera Java
        encounters_list = []
        for game, locs in grouped_encounters.items():
            encounters_list.append({
                "gamesString": game,
                "locations": locs
            })

        # 3. Evoluciones
        evo_url = spec_res['evolution_chain']['url']
        evo_res = requests.get(evo_url).json()
        evolutions = []
        curr = evo_res['chain']
        while curr:
            evo_info = get_basic_info(curr['species']['name'])
            if evo_info: evolutions.append(evo_info)
            curr = curr['evolves_to'][0] if curr['evolves_to'] else None

        return jsonify({
            "id": main_data['id'],
            "name": main_data['name'],
            "spriteUrl": main_data['sprites']['front_default'],
            "types": [t['type']['name'] for t in main_data['types']],
            "encounters": encounters_list,
            "evolutions": evolutions,
            "varieties": varieties
        })
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)