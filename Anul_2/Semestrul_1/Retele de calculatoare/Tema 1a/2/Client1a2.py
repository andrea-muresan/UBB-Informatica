import socket
import struct

def main():
    c = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('127.0.0.1', 1234)

    c.connect(server_address)

    # Read and send the string
    buffer = input("String: ")
    c.send(buffer.encode())

    # Receive the number of spaces
    nr = int(c.recv(2).decode())

    print("Number of spaces: " + str(nr))

    c.close()

if __name__ == "__main__":
    main()