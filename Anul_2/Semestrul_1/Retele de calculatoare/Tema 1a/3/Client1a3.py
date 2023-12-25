import socket
import struct

def main():
    c = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('127.0.0.1', 1234)

    c.connect(server_address)

    buffer = input("String: ");

    c.send(buffer.encode())
    rez = c.recv(1024).decode()

    print(rez)

    c.close()

if __name__ == "__main__":
    main()