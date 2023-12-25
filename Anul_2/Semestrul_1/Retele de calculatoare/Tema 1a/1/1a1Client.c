#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>

int main() {
        int c;
        struct sockaddr_in server;
        uint16_t n, numbers[100], sum;

        c = socket(AF_INET, SOCK_STREAM, 0);
        if (c < 0) {
                printf("Socket creation failed - client\n");
                exit(1);
        }

        memset(&server, 0, sizeof(server));
        server.sin_port = htons(1235);
        server.sin_family = AF_INET;
        server.sin_addr.s_addr = inet_addr("127.0.0.1");

        if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
                printf("Connection failed\n");
                exit(1);
        }

	printf("n = ");
        scanf("%hu", &n);
        if (n > 100) {
                printf("N <= 100");
                exit(1);
        }
        for (int i = 0; i < n; i++) {
                printf("Number: ");
                scanf("%hu", &numbers[i]);
                numbers[i] = htons(numbers[i]);
        }
        n = htons(n);

        send(c, &n, sizeof(n), 0);
        send(c, numbers, ntohs(n) * sizeof(uint16_t), 0);
        recv(c, &sum, sizeof(sum), 0);

        sum = ntohs(sum);
        printf("The sum is %hu\n", sum);
        close(c);

}