import socket
import json

def main():
    c = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('127.0.0.1', 1234)

    c.connect(server_address)

    nr = input("Nr: ")
    c.send(str(nr).encode())

    data = c.recv(1234)
    received_list = json.loads(data.decode())

    print(received_list)

    c.close()

if __name__ == "__main__":
    main()