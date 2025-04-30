import React, { useState, useEffect } from "react";
import { Form, Button, Container, Row, Col, Alert } from "react-bootstrap";
import { Transacao } from "../api/model";
import { postTransacao } from "../api";
import CurrencyInput from "./CurrencyInput";
import { getCurrentIsoWithTimezoneOffset } from "../utils/formatDateWithOffset";

const TransacaoForm: React.FC = () => {
    const [valor, setValor] = useState<number>(0);
    const [mensagem, setMensagem] = useState<string | null>(null);
    const [erro, setErro] = useState<boolean>(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const dataHora = getCurrentIsoWithTimezoneOffset(); // gera automaticamente no envio
        const novaTransacao: Transacao = { valor, dataHora };

        try {
            await postTransacao(novaTransacao);
            setMensagem("Transação enviada com sucesso!");
            setErro(false);
            setValor(0); // opcional: limpa o campo
        } catch (error) {
            console.error("Erro ao enviar transação:", error);
            setMensagem("Erro ao enviar transação.");
            setErro(true);
        }
    };

    useEffect(() => {
        if (mensagem) {
            const timer = setTimeout(() => setMensagem(null), 5000); // 5 segundos
            return () => clearTimeout(timer); // limpa timeout se desmontar antes
        }
    }, [mensagem]);

    return (
        <Container className="mt-5">
            <Row className="justify-content-md-left">
                <Col md={6}>
                    <h3 className="mb-4">Nova Transação</h3>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="formValor">
                            <Form.Label>Valor</Form.Label>
                            <CurrencyInput value={valor} onChange={setValor} />
                        </Form.Group>

                        <Button 
                            variant="primary" 
                            type="submit"
                            disabled={!!mensagem}
                        >
                            Enviar
                        </Button>
                    </Form>

                    {mensagem && (
                        <Alert
                            variant={erro ? "danger" : "success"}
                            className="mt-3"
                            dismissible
                            onClose={() => setMensagem(null)}
                        >
                            {mensagem}
                        </Alert>
                    )}

                </Col>
            </Row>
        </Container>
    );
};

export default TransacaoForm;
