import socket

client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server_address = ('localhost', 12345)


nr = str(input("Enter a number: "))

client_socket.sendto(nr.encode('utf-8'), server_address)

response, server_address = client_socket.recvfrom(1024)

print(bool(int(response.decode())))


client_socket.close()