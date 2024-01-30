import socket
import json

client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server_address = ('localhost', 12345)


msg = input("Enter a number: ")

client_socket.sendto(msg.encode('utf-8'), server_address)

data, server_address = client_socket.recvfrom(1024)
response = json.loads(data.decode('utf-8'))

print(response)


client_socket.close()