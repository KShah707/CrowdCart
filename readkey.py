import json

with open("secrets.json") as secretsfile:
	secrets = json.load(secretsfile)
	indico_key = secrets['indico_key']
