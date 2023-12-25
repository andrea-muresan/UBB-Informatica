import socket
import os

def server_client(c):
    #data = c.recv(1024).decode()
    #parts = data.split(':')

    #buffer = parts[0]
    #index = int(parts[1])
    #length = int(parts[2])

    buffer = c.recv(1024).decode()
    index = int(c.recv(2).decode())
    length = int(c.recv(2).decode())


    rez = buffer[index: index+length]
    c.send(rez.encode())

    c.close()
    os._exit(0)

def main():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('127.0.0.1', 1234)

    s.bind(server_address)
    s.listen(5)

    while True:
        try:
            c, addr = s.accept()
            print("Conected...")

            if os.fork() == 0:
                s.close()
                server_client(c)
                return 0
            else:
                c.close()
        except:
            print("Exit")
            exit(1)

if __name__ == "__main__":
    main()