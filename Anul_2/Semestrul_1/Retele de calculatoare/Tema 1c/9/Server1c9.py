import socket
import socketserver
import json
import time

def function():
    return int(time.time())


def main():
    # Create a UDP socket
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # Bind the socket to a specific address and port
    server_address = ('localhost', 12345)
    server_socket.bind(server_address)

    print('Server listening on {}:{}'.format(*server_address))

    while True:
        print("Waiting...")
        msg, client_address = server_socket.recvfrom(1024)
        msg = msg.decode('utf-8')
        print('Received message {} from {}'.format(msg, client_address))

        response = function()

        server_socket.sendto(str(response).encode('utf-8'), client_address)

if __name__ == "__main__":
    main()