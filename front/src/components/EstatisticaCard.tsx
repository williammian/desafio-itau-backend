import React, { useEffect, useState } from "react";
import { Card, Spinner, Alert, Container, Row, Col } from "react-bootstrap";
import { Estatistica } from "../api/model";
import { getEstatistica } from "../api";

const EstatisticaCard: React.FC = () => {
    const [estatistica, setEstatistica] = useState<Estatistica | null>(null);
    const [carregando, setCarregando] = useState(true);
    const [erro, setErro] = useState<string | null>(null);

    useEffect(() => {
        const carregarEstatistica = async () => {
            try {
                const dados = await getEstatistica();
                setEstatistica(dados);
            } catch (err) {
                console.error(err);
                setErro("Erro ao carregar estatísticas.");
            } finally {
                setCarregando(false);
            }
        };

        carregarEstatistica();
    }, []);

    if (carregando) {
        return <Spinner animation="border" />;
    }

    if (erro) {
        return <Alert variant="danger">{erro}</Alert>;
    }

    if (!estatistica) {
        return null;
    }

    return (
        <Container>
            <Row className="justify-content-md-left">
                <Col md={6}>
                    <Card className="mt-5">
                        <Card.Body>
                            <Card.Title>Estatísticas</Card.Title>
                            <ul>
                                <li>Total de transações: {estatistica.count}</li>
                                <li>Soma: R$ {estatistica.sum.toFixed(2)}</li>
                                <li>Média: R$ {estatistica.avg.toFixed(2)}</li>
                                <li>Máximo: R$ {estatistica.max.toFixed(2)}</li>
                                <li>Mínimo: R$ {estatistica.min.toFixed(2)}</li>
                            </ul>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default EstatisticaCard;
