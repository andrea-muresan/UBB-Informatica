import socket
import socketserver
import json

def function(nr):
    div = []
    for i in range(1, nr+1):
        if nr % i == 0:
            div.append(i)

    return div


def main():
    # Create a UDP socket
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # Bind the socket to a specific address and port
    server_address = ('localhost', 12345)
    server_socket.bind(server_address)

    print('Server listening on {}:{}'.format(*server_address))

    while True:
        print("Wait for a number...")
        nr, client_address = server_socket.recvfrom(1024)
        nr = int(nr.decode('utf-8'))
        print('Received message {} from {}'.format(str(nr), client_address))

        response = function(nr)
        json_data = json.dumps(response)

        server_socket.sendto(json_data.encode('utf-8'), client_address)

if __name__ == '__main__':
    main()