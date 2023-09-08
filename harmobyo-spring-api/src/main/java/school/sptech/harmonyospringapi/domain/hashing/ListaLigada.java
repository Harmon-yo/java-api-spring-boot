package school.sptech.harmonyospringapi.domain.hashing;

import school.sptech.harmonyospringapi.service.pedido.dto.PedidoExibicaoDto;

public class ListaLigada {

    private Node head;

    public ListaLigada() {
        this.head = new Node(null);
    }

    public void insereNode(PedidoExibicaoDto pedidoExibicaoDto){
        Node novo = new Node(pedidoExibicaoDto);
        novo.setNext(head.getNext());
        head.setNext(novo);
    }

    public void exibe(){
        Node atual = head.getNext();
        while (atual != null){
            System.out.println(atual.getPedido());
            atual = atual.getNext();
        }
    }

    public Node buscaNode(PedidoExibicaoDto pedidoExibicaoDto){
        Node atual = head.getNext();
        while (atual != null){
            if (atual.getPedido() == pedidoExibicaoDto){
                return atual;
            } else {
                atual = atual.getNext();
            }
        }
        return null;
    }

    public boolean removeNode(PedidoExibicaoDto pedidoExibicaoDto){
        Node ant = head;
        Node atual = head.getNext();
        while (atual != null){
            if (atual.getPedido() == pedidoExibicaoDto){
                ant.setNext(atual.getNext());
                return true;
            } else {
                ant = atual;
                atual = atual.getNext();
            }
        }
        return false;
    }


    public void concatenar(ListaLigada lista){
        Node ant = getHead();
        Node atual = getHead().getNext();

        while (atual != null){
            ant = atual;
            atual = atual.getNext();
        }

        ant.setNext(lista.getHead());
    }

    public boolean compara(ListaLigada lista){
        Node atual = getHead();
        Node comparador = lista.getHead();

        while (atual != null && comparador != null){
            if (atual.getPedido() == comparador.getPedido()){
                atual = atual.getNext();
                comparador = comparador.getNext();
            } else {
                return false;
            }
        }
        return true;
    }

    public void inverte(){
        Node ant = null;
        Node atual = getHead().getNext();
        Node proximo;

        while (atual != null){
            proximo = atual.getNext();
            atual.setNext(ant);
            ant = atual;
            atual = proximo;
        }

        getHead().setNext(ant);
    }

    public int getTamanho(){
        Node atual = head.getNext();
        int tam = 0;
        while (atual != null){
            tam++;
            atual = atual.getNext();
        }
        return tam;
    }

    public Node getHead() {
        return head;
    }

    public void exibeRecursivo(Node atual){
        if (atual != null){
            System.out.println(atual.getPedido());
            atual = atual.getNext();
            exibeRecursivo(atual);
        }
    }

    public Node buscaNodeRecursivo(Node atual, String status){
        if (atual == null){
            return null;
        }
        if (atual.getPedido().getStatus().getDescricao() == status){
            return atual;
        }
        return buscaNodeRecursivo(atual.getNext(), status);
    }

    public boolean removeNodeRecursivo(Node ant, String status){
        Node atual = ant.getNext();
        if (atual == null){
            return false;
        }
        if (atual.getPedido().getStatus().getDescricao() == status){
            ant.setNext(atual.getNext());
            return true;
        }
        return removeNodeRecursivo(atual, status);
    }

    public int getTamanhoRecursivo(int tam, Node atual){
        if (atual != null){
            tam++;
            atual = atual.getNext();

            getTamanhoRecursivo(tam, atual);
        }
        return tam;
    }

    public Node getElemento(int indice){
        Node pedido = null;
        Node atual = getHead().getNext();

        for (int i = 0; i <= indice; i++){
            if (indice == i){
                pedido = atual;
            }
            atual = atual.getNext();
        }
        return pedido;
    }

    public boolean removeOcorrencia(String status){
        Node ant = head;
        Node atual = head.getNext();
        boolean vali = false;
        while (atual != null){
            if (atual.getPedido().getStatus().getDescricao() == status){
                ant.setNext(atual.getNext());
            } else {
                ant = atual;
                atual = atual.getNext();
            }
        }
        if (vali){
            return true;
        }
        return false;
    }
}
