import React from "react";
import { FormControl } from "react-bootstrap";

interface CurrencyInputProps {
    value: number;
    onChange: (value: number) => void;
}

const CurrencyInput: React.FC<CurrencyInputProps> = ({ value, onChange }) => {
    const formatador = new Intl.NumberFormat("pt-BR", {
        style: "currency",
        currency: "BRL",
        minimumFractionDigits: 2,
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const raw = e.target.value.replace(/\D/g, ""); // remove tudo que não for dígito
        const valor = parseFloat(raw) / 100;
        onChange(valor);
    };

    const formatado = formatador.format(value);

    return (
        <FormControl
            type="text"
            value={formatado}
            onChange={handleChange}
        />
    );
};

export default CurrencyInput;
