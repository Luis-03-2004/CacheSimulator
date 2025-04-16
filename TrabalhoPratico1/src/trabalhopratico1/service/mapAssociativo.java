package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import model.FileManager;
import model.mapeamento;



public class mapAssociativo extends mapeamento{
    String caminhoteste;
    Random random ;
    Scanner sc = new Scanner(System.in);
    Queue <String> fila ;
    LinkedHashMap<String, Long> memCache;
    HashSet <String> memCacheRandom;
    HashMap<Integer,String> memRandom;
    ArrayList<String> LRU;
    ArrayList<String> LFU;

    public mapAssociativo(String caminhoteste , String config) {
        super(config);
        this.caminhoteste = caminhoteste;
        this.config = config;
        this.random = new Random(); 
    }

    public void randomAssociativo(){
        setTam();
        calcbits();
        iniciarVariaveis();
        ArrayList<String> teste = FileManager.stringReader(caminhoteste);               
                    memCacheRandom = new HashSet<>();
                    memRandom = new HashMap<>();
                    for (String linha : teste) {
                        Long acesso = Long.parseLong(linha);
                        String stringBin = intToBinaryString(acesso, (int)bitsend);
                        String Stag = stringBin.substring(0,((int)bitstag));

                        if ((memCacheRandom.contains(Stag))){
                            hit++;
                        }else {
                            miss++;
                            if (memCacheRandom.size() >= this.qtdlinha){ 
                                //substituirAleatoriamente();
                                int indice = random.nextInt(memRandom.size());
                                memCacheRandom.remove(memRandom.remove(indice));

                                memRandom.put(indice, Stag);
                                memCacheRandom.add(Stag);
                            }   
                                
                                memRandom.put(memRandom.size(), Stag);
                                memCacheRandom.add(Stag);
                        }
                    }
                    taxahit = (double)hit / (double)teste.size();
                    taxaerro = (double)miss / (double)teste.size();
                    setarValoresFinais(hit,miss,taxahit,taxaerro);
    }

    public void fifoAssociativo(){
        setTam();
        calcbits();
        iniciarVariaveis();
        ArrayList<String> teste = FileManager.stringReader(caminhoteste);
        memCache = new LinkedHashMap<>();
        fila = new LinkedList<>();
        for (String linha : teste) {
            Long acesso = Long.parseLong(linha);
            String stringBin = intToBinaryString(acesso, (int)bitsend);
            String Stag = stringBin.substring(0,((int)bitstag));
            
            if ((memCache.containsKey(Stag)) ){
                hit++;
              // System.out.println("Acerto");
            }else {
                miss++;
                if ((memCache.size() >= this.qtdlinha) ){
                    substituirFIFO();
                  // System.out.println("Erro");
                }
                memCache.put(Stag, pal);
                fila.offer(Stag);
            }
        }
        taxahit = (double)hit / (double)teste.size();
        taxaerro = (double)miss / (double)teste.size();
        setarValoresFinais(hit,miss,taxahit,taxaerro);


    }
    public void lruAssociativo(){
        setTam();
        calcbits();
        iniciarVariaveis();
        ArrayList<String> teste = FileManager.stringReader(caminhoteste);
        memCache = new LinkedHashMap<>();
        LRU = new ArrayList<>();
        for (String linha : teste) {
            Long acesso = Long.parseLong(linha);
            String stringBin = intToBinaryString(acesso, (int)bitsend);
            String Stag = stringBin.substring(0,((int)bitstag));
            
            if ((memCache.containsKey(Stag)) ){
                hit++;
               atualizaLRU(Stag);
            }else {
                miss++;
                if ((memCache.size() >= this.qtdlinha) ){
                    substituirLRU();
                  // System.out.println("Erro");
                }
                memCache.put(Stag, pal);
                LRU.add(Stag);
            }
        }
        taxahit = (double)hit / (double)teste.size();
        taxaerro = (double)miss / (double)teste.size();
        setarValoresFinais(hit,miss,taxahit,taxaerro);


    }

    public void lfuAssociativo(){
        setTam();
        calcbits();
        iniciarVariaveis();
        ArrayList<String> teste = FileManager.stringReader(caminhoteste);
        memCache = new LinkedHashMap<>();

                for (String linha : teste) {
                    Long acesso = Long.parseLong(linha);
                    String stringBin = intToBinaryString(acesso, (int)bitsend);
                    String Stag = stringBin.substring(0,((int)bitstag));
                    
                    if ((memCache.containsKey(Stag)) ){
                        hit++;
                        atualizaLFU(Stag);

                    }else {
                        miss++;
                        if ((memCache.size() >= this.qtdlinha) ){
                            substituirLFU();
                        }
                        memCache.put(Stag, 1L);
                    }
                }
                taxahit = (double)hit / (double)teste.size();
                taxaerro = (double)miss / (double)teste.size();
                setarValoresFinais(hit,miss,taxahit,taxaerro);


    }

    protected void substituirFIFO(){
        if (!fila.isEmpty()) {
            String chave = fila.poll(); // Remove o primeiro elemento da fila
            memCache.remove(chave); // Remove o elemento da cache
        }
    }


    // private void substituirAleatoriamente() {

    //     Long indice = random.nextLong(memCache.size());
    //     String tag = (String) memCache.keySet().stream().toArray()[indice.intValue()];
    //     memCache.remove(tag);
    //     }

    protected void substituirLRU(){
        if(!LRU.isEmpty()){
            String lru = LRU.get(0);
            memCache.remove(lru);
            LRU.remove(lru);
        }
    }

    protected void atualizaLRU(String Stag){
        LRU.remove(Stag);
        LRU.add(Stag);
    }

    protected void atualizaLFU(String Stag) {
        Long acessos = memCache.get(Stag);
        acessos++;
        memCache.put(Stag, acessos);

    }

    protected void substituirLFU(){

        String lfu = null;
        Long menor = Long.MAX_VALUE;

        for (String key : memCache.keySet()) {
            if (memCache.get(key) == 1L) {
                lfu = key;
                break; 
            } else if (memCache.get(key) < menor) {
                menor = memCache.get(key);
                lfu = key;
            }
        }
        if (lfu != null) {
            memCache.remove(lfu);
        }
    }

    public void calcbits(){
        
        if (this.qtdendereco == 1){
            this.bitsend = 1;
        }else {
            this.bitsend = Math.log(qtdendereco)/Math.log(2);
        }
        if (this.pal==1){
            this.bitspal = 1;
        }else {
           this.bitspal = Math.log(this.pal)/Math.log(2);
        }
         this.bitstag = this.bitsend - this.bitspal;

    }
    public void iniciarVariaveis(){
        this.taxahit = 0;
        this.taxaerro = 0;
        this.miss = 0L;
        this.hit = 0L;
    }

    public void setarValoresFinais(Long hit, Long miss, double taxahit, double taxaerro){
        setTaxaerro(this.taxaerro*100);
        setTaxahit(this.taxahit*100);
        setHit(this.hit);
        setMiss(this.miss);
    }

}
