import socket
import socketserver
import json

def cmmdc_function(a, b):
    while b:
        r = a % b
        a = b
        b = r

    return a

def cmmmc_function(a, b):
    return a * b // cmmdc_function(a, b)


def main():
    # Create a UDP socket
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # Bind the socket to a specific address and port
    server_address = ('localhost', 12345)
    server_socket.bind(server_address)

    print('Server listening on {}:{}'.format(*server_address))

    while True:
        print("Waiting...")

        a, client_address = server_socket.recvfrom(1024)
        a = int(a.decode('utf-8'))
        print('Received index {} from {}'.format(str(a), client_address))

        b, client_address = server_socket.recvfrom(1024)
        b = int(b.decode('utf-8'))
        print('Received length {} from {}'.format(str(b), client_address))

        cmmdc = cmmdc_function(a, b)
        server_socket.sendto(str(cmmdc).encode('utf-8'), client_address)

        cmmmc = cmmmc_function(a, b)
        server_socket.sendto(str(cmmmc).encode('utf-8'), client_address)

if __name__ == '__main__':
    main()