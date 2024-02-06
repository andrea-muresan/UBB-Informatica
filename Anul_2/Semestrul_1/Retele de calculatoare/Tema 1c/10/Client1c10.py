import socket

# Create a UDP socket
client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

# Server address and port
server_address = ('localhost', 12345)

# Computer name to send
computer_name = input("Enter name: ")

# Send the computer name to the server
client_socket.sendto(computer_name.encode('utf-8'), server_address)

# Wait for a response from the server
response, server_address = client_socket.recvfrom(1024)

# Decode the response and print the IP address or error message
print(response.decode('utf-8'))

# Close the socket
client_socket.close()