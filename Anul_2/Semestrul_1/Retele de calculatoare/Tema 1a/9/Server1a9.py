import os
import socket
import json

def client_server(c):

    data = c.recv(1024).decode()
    received_data = json.loads(data)

    data2 = c.recv(1024).decode()
    received_data2 = json.loads(data2)

    rez = [el for el in received_data2 if el not in received_data]

    json_rez = json.dumps(rez)
    c.send(json_rez.encode())

    c.close()
    os._exit(0)

def main():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('127.0.0.1', 1234)

    s.bind(server_address)
    s.listen(5)

    while True:
        try:
            c, addr = s.accept()
            print("Connected...")

            if os.fork() == 0:
                s.close()
                client_server(c)
                return 0
            else:
                c.close()
        except:
            print("Exit")
            exit(1)

if __name__ == "__main__":
    main()