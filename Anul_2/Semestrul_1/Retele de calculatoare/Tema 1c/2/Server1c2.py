import socket
import socketserver

def is_prime(nr):
    if nr < 2:
        return False

    if nr > 2 and nr % 2 == 0:
        return False

    i = 3
    while i * i <= nr:
        if nr % i == 0:
            return False
        i += 1

    return True

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
        nr = int(nr)

        response = is_prime(nr)

        server_socket.sendto(str(int(response)).encode('utf-8'), client_address)

if __name__ == '__main__':
    main()