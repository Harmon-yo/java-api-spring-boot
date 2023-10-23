package school.sptech.harmonyospringapi.service.pedido.fila;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.harmonyospringapi.domain.fila.FilaEspera;
import school.sptech.harmonyospringapi.domain.fila.ListaFilas;
import school.sptech.harmonyospringapi.service.pedido.PedidoService;
import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDto;
import school.sptech.harmonyospringapi.utils.NodeObj;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FilaEsperaService {

    @Autowired
    private PedidoService pedidoService;

    private ListaFilas listaFilas;

    public FilaEsperaService() {
        this.listaFilas = new ListaFilas();
    }

    //pesquisa o pedido pai e já cria uma fila pronta para inserir o pedido
    public FilaEspera criarFila(PedidoExibicaoDto pai){
        FilaEspera novaFila = new FilaEspera(pai);
        this.listaFilas.novaFila(novaFila);
        return novaFila;
    }

    public PedidoExibicaoDto adicionarPedidoFilaEspera(int fkUsuario, String dataAula){

        // formatando data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dataAulaFormatada = LocalDateTime.parse(dataAula, formatter);

        // buscando todos os pedidos com essa data e usuario
        List<PedidoExibicaoDto> pedidoExibicaoDtos = this.pedidoService.buscarAulasPorIdUsuarioEDataAula(fkUsuario, dataAulaFormatada);

        // buscando o ultimo pedido inserido
        PedidoExibicaoDto pedidoExibicaoDto = this.pedidoService.buscarPorIdParaExibicao(
                pedidoExibicaoDtos.get(0).getId());

        // buscando o pai da fila de espera
        PedidoExibicaoDto pai = this.listaFilas.buscarPai(pedidoExibicaoDto.getId());

        // se o a fila não existir, cria uma nova fila e insere o pedido
        if (pai == null){
            // cria a fila com o pedido pai
            FilaEspera filaEspera = criarFila(this.pedidoService.buscarPorIdParaExibicao(
                    pedidoExibicaoDtos.get(pedidoExibicaoDtos.size()-1).getId()));
            // atualiza o status do pedido inserido
            this.pedidoService.atualizarStatus(
                    this.pedidoService.buscarPorId(pedidoExibicaoDto.getId()), "Em Fila");
            // insere o pedido na fila
            System.out.println("Inserindo pedido " + pedidoExibicaoDto.getId() + " na fila");
            filaEspera.insert(pedidoExibicaoDto);
            // retorna o pedido inserido
            return filaEspera.peek();
        }
        // caso exista a fila, só insere o pedido na fila
        else {
            // busca a fila
            NodeObj<FilaEspera> filaEspera = this.listaFilas.buscaFila(pai);
            // atualiza o status do pedido inserido
            this.pedidoService.atualizarStatus(
                    this.pedidoService.buscarPorId(pedidoExibicaoDto.getId()), "Em Fila");
            // insere o pedido na fila
            System.out.println("Inserindo pedido " + pedidoExibicaoDto.getId() + " na fila");
            filaEspera.getInfo().insert(pedidoExibicaoDto);
            // retorna o pedido inserido
            return filaEspera.getInfo().peek();
        }
    }

    public PedidoExibicaoDto removerPrimeiroPedidoFilaEspera(PedidoExibicaoDto pedidoExibicaoDto){
        PedidoExibicaoDto pai = this.listaFilas.buscarPai(pedidoExibicaoDto.getId());
        if (pai == null){
            return null;
        } else {
            NodeObj<FilaEspera> filaEspera = this.listaFilas.buscaFila(pai);

            this.pedidoService.atualizarStatus(
                    this.pedidoService.buscarPorId(pedidoExibicaoDto.getId()), "Pendente");

            return filaEspera.getInfo().poll();
        }
    }

    public int pegarPosicaoNaFila(PedidoExibicaoDto pedidoExibicaoDto){
        PedidoExibicaoDto pai = this.listaFilas.buscarPai(pedidoExibicaoDto.getId());
        if (pai == null){
            return 0;
        } else {
            NodeObj<FilaEspera> filaEspera = this.listaFilas.buscaFila(pai);

            return filaEspera.getInfo().posicaoNaFila(pedidoExibicaoDto);
        }
    }

    public PedidoExibicaoDto buscarPai(int idPedido){
        return this.listaFilas.buscarPai(idPedido);
    }

    public List<PedidoExibicaoDto> listarFilaDeEspera(int idPai){
        PedidoExibicaoDto pai = this.listaFilas.buscarPai(idPai);
        if (pai == null){
            return null;
        } else {
            NodeObj<FilaEspera> filaEspera = this.listaFilas.buscaFila(pai);
            System.out.println("Listando fila do pedido " + filaEspera.getInfo().getPai().getId());
            System.out.println("Fila: " + filaEspera.getInfo());
            return filaEspera.getInfo().getPedidos();
        }
    }

    public List<FilaEspera> listarFilas(){
        return this.listaFilas.getFilas();
    }

    public boolean isEmpty(){
        return this.listaFilas.isEmpty();
    }

    public void adicionarBanco(){
        List<PedidoExibicaoDto> pedidoExibicaoDtos = this.pedidoService.obterTodos();
        List<PedidoExibicaoDto> listaPedidos = this.pedidoService.inverterLista(pedidoExibicaoDtos);

        for (PedidoExibicaoDto pedidoExibicaoDto : listaPedidos){
            if (pedidoExibicaoDto.getStatus().getDescricao().equals("Em Fila")){
                System.out.println("Adicionando pedido " + pedidoExibicaoDto.getId() + " na fila");
                PedidoExibicaoDto pai = this.listaFilas.buscarPai(pedidoExibicaoDto.getId());
                if (pai == null){
                    FilaEspera filaEspera = criarFila(this.pedidoService.buscarPorIdParaExibicao(pai.getId()));
                    filaEspera.insert(pedidoExibicaoDto);
                } else {
                    NodeObj<FilaEspera> filaEspera = this.listaFilas.buscaFila(pai);
                    filaEspera.getInfo().insert(pedidoExibicaoDto);
                }
            }
        }
    }

    public ListaFilas getListaFilas() {
        return listaFilas;
    }

    public void setListaFilas(ListaFilas listaFilas) {
        this.listaFilas = listaFilas;
    }
}
