import socket
import struct

def main():
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('127.0.0.1', 1234)

    client_socket.connect(server_address)

    # Send the first string with its length
    string1 = input("String1: ")
    length1 = len(string1) + 1
    packed_length1 = struct.pack('!H', length1)
    client_socket.send(packed_length1)
    client_socket.send(string1.encode())

    # Send the second string with its length
    string2 = input("String2: ")
    length2 = len(string2) + 1
    packed_length2 = struct.pack('!H', length2)
    client_socket.send(packed_length2)
    client_socket.send(string2.encode())

    # Receive the most frequently used character and its frequency
    character = client_socket.recv(1).decode()
    frequency = struct.unpack('!H', client_socket.recv(2))[0]

    print(f"Most common character: {character}, frequency {frequency}")

    client_socket.close()

if __name__ == "__main__":
    main()