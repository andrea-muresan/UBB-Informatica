import socket
import struct
import os

def server_client(c):
    # Receive the string
    buffer = c.recv(1024).decode()

    buffer = buffer[::-1]

    c.send(buffer.encode())

    c.close()
    os._exit(0)

def main():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('127.0.0.1', 1234)

    s.bind(server_address)
    s.listen(5)

    while True:
        c, addr = s.accept()
        print("Client connected...")

        if os.fork() == 0:
            s.close()
            server_client(c)
            return 0
        else:
            c.close()



if __name__ == "__main__":
    main()