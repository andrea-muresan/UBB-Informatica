import socket

client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server_address = ('localhost', 12345)


msg = input("Enter a message: ")

client_socket.sendto(msg.encode('utf-8'), server_address)

response, server_address = client_socket.recvfrom(1024)

print(int(response.decode()))


client_socket.close()