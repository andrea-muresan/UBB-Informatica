try:
    x = int(input())
    if x < 3:
        raise ValueError
except ValueError:
    print("nu")
