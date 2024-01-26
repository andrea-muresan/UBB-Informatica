import socket

# Create a UDP socket
server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

# Bind the socket to a specific address and port
server_address = ('localhost', 12345)
server_socket.bind(server_address)

print('Server listening on {}:{}'.format(*server_address))

while True:
    # Wait for a message from the client
    print('Waiting for a message...')
    nr1, client_address = server_socket.recvfrom(1024)
    nr1 = int(nr1)

    nr2, client_address = server_socket.recvfrom(1024)
    nr2 = int(nr2)
    # Print the received message and client address
    # print('Received message "{}" from {}'.format(data.decode('utf-8'), client_address))

    # Send a response back to the client
    suma = nr1 + nr2
    suma = str(suma)
    # response = 'Message received successfully!'
    server_socket.sendto(suma.encode('utf-8'), client_address)