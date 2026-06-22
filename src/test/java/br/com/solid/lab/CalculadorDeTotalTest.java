package br.com.solid.lab;

import br.com.solid.lab.descontos.DescontoMaiorQueTotal;
import br.com.solid.lab.pedidos.CalculadorDeTotal;
import br.com.solid.lab.pedidos.Cliente;
import br.com.solid.lab.pedidos.Pedido;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadorDeTotalTest {
    @Test
    void naoPermiteTotalNegativoMesmoQuandoRegraRetornaValorInvalido() {
        Pedido pedido = new Pedido(new Cliente("Caio", "caio@example.com"));
        pedido.adicionarItem("Curso SOLID", 1, 100);

        CalculadorDeTotal calculador = new CalculadorDeTotal(List.of(
                new DescontoMaiorQueTotal()
        ));

        double total = calculador.calcular(pedido);

        assertEquals(0, total);
    }
}