package br.com.solid.lab;

import br.com.solid.lab.pedidos.Cliente;
import br.com.solid.lab.pedidos.Pedido;
import br.com.solid.lab.pedidos.ProcessadorDePedido;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProcessadorDePedidoTest {
    @Test
    void processaPedidoComCupomEDescontoDePedidoGrande() {
        Cliente cliente = new Cliente("Caio", "caio@example.com");
        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem("Código Limpo", 2, 80);
        pedido.adicionarItem("Refatoração", 1, 60);
        pedido.aplicarCupom("BOASVINDAS10");

        ProcessadorDePedido processador = new ProcessadorDePedido();
        String recibo = processador.processar(pedido);

        assertTrue(recibo.contains("Cliente: Caio"));
        assertTrue(recibo.contains("Total: 183.00"));
        assertEquals(1, processador.pedidosSalvos().size());
        assertEquals("caio@example.com|183.00", processador.pedidosSalvos().getFirst());
        assertEquals(1, processador.emailsEnviados().size());
        assertEquals("Para: caio@example.com | O total do seu pedido é 183.00", processador.emailsEnviados().getFirst());
    }
}
