import { titleCase } from "./biblioteca/formateadores";

describe('Formateador tests', () => {
    test('Formateado', () => {
        let cadena = 'BUENAS TARDES'
        expect(titleCase(cadena)).toBe('Buenas Tardes')
    })
})