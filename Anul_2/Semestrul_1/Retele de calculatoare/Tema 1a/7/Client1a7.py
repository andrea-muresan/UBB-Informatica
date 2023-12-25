import socket

def main():
    c = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('127.0.0.1', 1234)

    c.connect(server_address)

    buffer = input("Enter string:")
    c.send(buffer.encode())
    index = input("Enter index:")
    c.send(str(index).encode())
    length = input("Enter length:")
    c.send(str(length).encode())

    # data = f"{buffer}:{index}:{length}"
    # c.send(data.encode())'''


    rez = c.recv(1024).decode()
    print(rez)

    c.close()

if __name__ == "__main__":
    main()