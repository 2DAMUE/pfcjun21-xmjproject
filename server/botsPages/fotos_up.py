string = '<li data-bi-id="c2c1c2c1c1m1r5a3" data-bi-name="c2c1c2c1c1m1r5a3">Xbox SeriesX|S<br/>Xbox One<br/>Windows 10</li>'

plataformas = []
abierto = False
actual = ''
primera_vez = True
for char in string:
    if char == '<':
        abierto = True
        if primera_vez:
            primera_vez = False
        else:
            plataformas.append(actual)

    elif char == '>':
        abierto = False
        actual=''
    elif abierto == False:
        actual += char
    
print(plataformas)