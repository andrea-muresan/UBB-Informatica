import socket
import struct
import os
import json

def server_client(c):
    nr = int(c.recv(2).decode())

    divizori = []
    for i in range(1, nr+1):
        if nr % i == 0:
            divizori.append(i)

    json_data = json.dumps(divizori)
    c.send(json_data.encode())

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
                server_client(c)
                return 0
            else:
                c.close()
        except:
            print("Close")
            exit(1)

if __name__ == "__main__":
    main()