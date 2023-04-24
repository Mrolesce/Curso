import { titleCase } from "./biblioteca/formateadores";

describe('Test de los formateadores', () => {
    test('titleCase test', () => {
        let cadena = 'BUENAS TARDES'
        expect(titleCase(cadena)).toBe('Buenas Tardes')
        expect(titleCase(cadena)).not.toBe('BUENAS TARDES')
    })
})