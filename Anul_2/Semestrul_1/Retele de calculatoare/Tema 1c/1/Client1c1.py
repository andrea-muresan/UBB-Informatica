import socket

# Create a UDP socket
client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

# Server address and port
server_address = ('localhost', 12345)

nr1 = str(input("Enter number: "))
client_socket.sendto(nr1.encode('utf-8'), server_address)

nr2 = str(input("Enter number: "))
client_socket.sendto(nr2.encode('utf-8'), server_address)

# Wait for a response from the serve:r
response, server_address = client_socket.recvfrom(1024)

# Print the response from the server
print('Received response from server: "{}"'.format(response.decode('utf-8')))

# Close the socket
client_socket.close()