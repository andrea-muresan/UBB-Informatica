import socket
import socketserver
import json

def function(msg, ch):
    return [idx for idx, letter in enumerate(msg) if letter == ch]


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

        ch, client_address = server_socket.recvfrom(1024)
        ch = ch.decode('utf-8')
        print('Received character {} from {}'.format(ch, client_address))

        response = function(msg, ch)
        json_data = json.dumps(response)

        server_socket.sendto(json_data.encode('utf-8'), client_address)

if __name__ == '__main__':
    main()