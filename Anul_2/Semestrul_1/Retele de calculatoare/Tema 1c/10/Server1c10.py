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
    main()root@LAPTOP-7V5C7GV:~/UDP# cat Server1c10.py
import socket
import socketserver

# Function to get IP address from computer name
def get_ip_address(computer_name):
    try:
        ip_address = socket.gethostbyname(computer_name)
        return ip_address
    except socket.error:
        return 'Error: Could not resolve the computer name to an IP address.'

class UDPHandler(socketserver.BaseRequestHandler):
    def handle(self):
        data = self.request[0].strip()
        socket = self.request[1]

        # Decode the received data (computer name)
        computer_name = data.decode('utf-8')

        # Get the IP address based on the computer name
        ip_address = get_ip_address(computer_name)

        # Send the IP address back to the client
        socket.sendto(ip_address.encode('utf-8'), self.client_address)

# Create the server with the UDP handler
server_address = ('localhost', 12345)
server = socketserver.UDPServer(server_address, UDPHandler)

print('Server listening on {}:{}'.format(*server_address))

# Start the server
server.serve_forever()