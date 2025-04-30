import axios from "axios";
import { Transacao, Estatistica } from "./model";
import { API_URL } from "../constants";

export const postTransacao = async (transacao: Transacao) => {
    await axios.post(`${API_URL}/transacao`, transacao);
};

export const getEstatistica = async (): Promise<Estatistica> => {
    const response = await axios.get<Estatistica>(`${API_URL}/estatistica`);
    return response.data;
};