import socket
import socketserver
import json

def function(msg, i, l):
    if i + l > len(msg):
        return "Date gresite"
    else:
        return msg[i: i+l]


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

        i, client_address = server_socket.recvfrom(1024)
        i = int(i.decode('utf-8'))
        print('Received index {} from {}'.format(str(i), client_address))

        l, client_address = server_socket.recvfrom(1024)
        l = int(l.decode('utf-8'))
        print('Received length {} from {}'.format(str(l), client_address))

        response = function(msg, i, l)

        server_socket.sendto(response.encode('utf-8'), client_address)

if __name__ == '__main__':
    main()