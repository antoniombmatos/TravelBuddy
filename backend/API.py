from http.server import BaseHTTPRequestHandler, HTTPServer
import json

class TravelBuddyHandler(BaseHTTPRequestHandler):
    # Este método é chamado quando a App tenta fazer Login (POST)
    def do_POST(self):
        # 1. Avisar a app que correu tudo bem (Código 200)
        self.send_response(200)
        self.send_header('Content-type', 'application/json')
        self.end_headers()

        # 2. Criar um utilizador falso para responder
        # Estes campos têm de ser iguais ao teu UserEntity no Android
        fake_user = {
            "id": 99,
            "name": "Viajante Teste",
            "email": "teste@aluno.pt",
            "passwordHash": "xxxxxx" 
        }

        # 3. Enviar a resposta para o Android
        self.wfile.write(json.dumps(fake_user).encode('utf-8'))
        print("--> SUCESSO: A App Android bateu à porta e eu respondi!")

# Configuração da porta
port = 3000
print(f"--- API TravelBuddy a correr na porta {port} ---")
print("À espera que faças Login no Android...")

server = HTTPServer(('localhost', port), TravelBuddyHandler)
server.serve_forever()