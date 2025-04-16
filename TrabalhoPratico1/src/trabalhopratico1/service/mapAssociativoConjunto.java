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

public class mapAssociativoConjunto extends mapeamento{
    String caminhoteste;
    public Long conjuntoSize;
    Scanner sc = new Scanner(System.in);
    Random random;
    private ArrayList<Queue<String>> fila;
    private ArrayList<LinkedHashMap<String, Long>> memCache;
    private ArrayList<HashMap<Integer,String>> randomCacheMap;
    private ArrayList<HashSet<String>> randomCacheSet;
    private ArrayList<ArrayList<String>> LRU;
    private ArrayList<ArrayList<String>> LFU;

      public mapAssociativoConjunto(Long conjuntoSize,String caminhoteste,String config) {
        super(config);
        this.conjuntoSize = conjuntoSize;
        this.caminhoteste = caminhoteste;
        this.config = config;
        this.random = new Random();
    }

    public void conjuntoRandom(){
        setTam();
        calcbits();
        iniciarVariaveis();
        ArrayList<String> teste = FileManager.stringReader(caminhoteste);
                randomCacheMap = new ArrayList<>();
                randomCacheSet = new ArrayList<>();
                
                for (int i = 0; i < (this.qtdlinha / this.conjuntoSize); i++) {
                    randomCacheMap.add(new HashMap<>());
                    randomCacheSet.add(new HashSet<>());
                }
                
                for (String linha : teste) {
                    Long acesso = Long.parseLong(linha);
                    String stringBin = intToBinaryString(acesso, (int)this.bitsend);
                    String Stag = stringBin.substring(0, ((int)this.bitstag));
                    String conjuntoBin = stringBin.substring((int)this.bitstag, (int)this.bitstag + (int)this.bitsconjunto);
                    Long conjuntoIndex = binaryToInt(conjuntoBin, (int)this.bitsconjunto);

                    if ((randomCacheSet.get(conjuntoIndex.intValue()).contains(Stag))) {
                        hit++;
                    } else {
                        miss++;
                        if (randomCacheMap.get(conjuntoIndex.intValue()).size() >= conjuntoSize) {

                            if (conjuntoSize == 1) {
                                // Para mapeamento direto, substitua diretamente
                                String oldTag = randomCacheMap.get(conjuntoIndex.intValue()).get(0);
                                randomCacheSet.get(conjuntoIndex.intValue()).remove(oldTag);
                                randomCacheMap.get(conjuntoIndex.intValue()).put(0, Stag);
                                randomCacheSet.get(conjuntoIndex.intValue()).add(Stag);
                            }
                            else{
                                int indice = random.nextInt((randomCacheMap.get(conjuntoIndex.intValue())).size());
                             randomCacheSet.get(conjuntoIndex.intValue()).remove(randomCacheMap.get(conjuntoIndex.intValue()).remove(indice));
                             randomCacheMap.get(conjuntoIndex.intValue()).put(indice, Stag);
                             randomCacheSet.get(conjuntoIndex.intValue()).add(Stag);
                            }
                        // substituirAleatoriamente(conjuntoIndex);
                        
                        }
                        randomCacheMap.get(conjuntoIndex.intValue()).put(randomCacheMap.get(conjuntoIndex.intValue()).size(),Stag);
                        randomCacheSet.get(conjuntoIndex.intValue()).add(Stag);
                    }
                }
                this.taxahit = (double)this.hit / (double)teste.size();
                this.taxaerro = (double)this.miss / (double)teste.size();
                setarValoresFinais(this.hit,this.miss,this.taxahit,this.taxaerro);
    }

    public void conjuntoFifo(){
        setTam();
        calcbits();
        iniciarVariaveis();
        ArrayList<String> teste = FileManager.stringReader(caminhoteste);
        memCache = new ArrayList<>();
        fila = new ArrayList<>();
        for (int i = 0; i < this.qtdlinha / this.conjuntoSize; i++) {
            memCache.add(new LinkedHashMap<>());
            fila.add(new LinkedList<>());
        }
        for (String linha : teste ){
            Long acesso = Long.parseLong(linha);
            String stringBin = intToBinaryString(acesso, (int)this.bitsend);
            String Stag = stringBin.substring(0, ((int)this.bitstag));
            String conjuntoBin = stringBin.substring((int)this.bitstag, (int)this.bitstag + (int)this.bitsconjunto);
            Long conjuntoIndex = binaryToInt(conjuntoBin, (int)this.bitsconjunto);

            if ((memCache.get(conjuntoIndex.intValue()).containsKey(Stag))) {
                hit++;
            } else {
                miss++;
                if (memCache.get(conjuntoIndex.intValue()).size() >= conjuntoSize) {
                    substituirFIFO(conjuntoIndex);
                }
                memCache.get(conjuntoIndex.intValue()).put(Stag, pal);
                fila.get(conjuntoIndex.intValue()).offer(Stag);
            }

        }

                this.taxahit = (double)this.hit / (double)teste.size();
                this.taxaerro = (double)this.miss / (double)teste.size();
                setarValoresFinais(this.hit,this.miss,this.taxahit,this.taxaerro);
    }

    public void cojuntoLru(){
        setTam();
        calcbits();
        iniciarVariaveis();
        ArrayList<String> teste = FileManager.stringReader(caminhoteste);
        memCache = new ArrayList<>();
        LRU = new ArrayList<>();
        for (int i = 0; i < this.qtdlinha / this.conjuntoSize; i++) {
            memCache.add(new LinkedHashMap<>());
            LRU.add(new ArrayList<>());
        }
        for (String linha : teste) {
            Long acesso = Long.parseLong(linha);
            String stringBin = intToBinaryString(acesso, (int)this.bitsend);
            String Stag = stringBin.substring(0, ((int)this.bitstag));
            String conjuntoBin = stringBin.substring((int)this.bitstag, (int)this.bitstag + (int)this.bitsconjunto);
            Long conjuntoIndex = binaryToInt(conjuntoBin, (int)this.bitsconjunto);

            if ((memCache.get(conjuntoIndex.intValue()).containsKey(Stag))) {
                hit++;
                atualizaLRU(conjuntoIndex, Stag);
            } else {
                miss++;
                if (memCache.get(conjuntoIndex.intValue()).size() >= conjuntoSize) {
                    substituirLRU(conjuntoIndex);
                }
                memCache.get(conjuntoIndex.intValue()).put(Stag, pal);
                LRU.get(conjuntoIndex.intValue()).add(Stag);
            }
        }

                this.taxahit = (double)this.hit / (double)teste.size();
                this.taxaerro = (double)this.miss / (double)teste.size();
                setarValoresFinais(this.hit,this.miss,this.taxahit,this.taxaerro);
    }
    public void conjuntoLfu(){
        setTam();
        calcbits();
        iniciarVariaveis();
        ArrayList<String> teste = FileManager.stringReader(caminhoteste);
        memCache = new ArrayList<>();
        LFU = new ArrayList<>();
        for (int i = 0; i < this.qtdlinha / this.conjuntoSize; i++) {
            memCache.add(new LinkedHashMap<>());
            LFU.add(new ArrayList<>());
        }
        for (String linha : teste) {
            Long acesso = Long.parseLong(linha);
            String stringBin = intToBinaryString(acesso, (int)this.bitsend);
            String Stag = stringBin.substring(0, ((int)this.bitstag));
            String conjuntoBin = stringBin.substring((int)this.bitstag, (int)this.bitstag + (int)this.bitsconjunto);
            Long conjuntoIndex = binaryToInt(conjuntoBin, (int)this.bitsconjunto);

            if ((memCache.get(conjuntoIndex.intValue()).containsKey(Stag))) {
                hit++;
                atualizaLFU(conjuntoIndex, Stag);
            } else {
                miss++;
                if (memCache.get(conjuntoIndex.intValue()).size() >= conjuntoSize) {
                    substituirLFU(conjuntoIndex);
                }
                memCache.get(conjuntoIndex.intValue()).put(Stag, 1L);
            }
        }
                this.taxahit = (double)this.hit / (double)teste.size();
                this.taxaerro = (double)this.miss / (double)teste.size();
                setarValoresFinais(this.hit,this.miss,this.taxahit,this.taxaerro);
    }
    // private void substituirAleatoriamente(Long conjuntoIndex) {
    //     //Long indice = random.nextLong(memCache.size());
    //    // String tag = (String) memCache.keySet().stream().toArray()[indice.intValue()];
    //    // memCache.remove(tag);
    //         int indice = random.nextInt((memCache.get(conjuntoIndex.intValue())).size());
    //        // Long indice = random.nextLong(memCache.get(conjuntoIndex.intValue()).size());
    //         String key = (String) memCache.get(conjuntoIndex.intValue()).keySet().stream().toArray()[indice];
    //         memCache.get(conjuntoIndex.intValue()).remove(key);        
    // }

    private void substituirFIFO(Long conjuntoIndex) {
        if (!fila.get(conjuntoIndex.intValue()).isEmpty()) {
            String chave = fila.get(conjuntoIndex.intValue()).poll();
            memCache.get(conjuntoIndex.intValue()).remove(chave);
        }
    }

    private void atualizaLRU(Long conjuntoIndex, String Stag) {
        LRU.get(conjuntoIndex.intValue()).remove(Stag);
        LRU.get(conjuntoIndex.intValue()).add(Stag);
    }
    private void substituirLRU(Long conjuntoIndex) {
        if (!LRU.get(conjuntoIndex.intValue()).isEmpty()) {
            String lru = LRU.get(conjuntoIndex.intValue()).get(0);
            memCache.get(conjuntoIndex.intValue()).remove(lru);
            LRU.get(conjuntoIndex.intValue()).remove(lru);
        }
    }
    protected void atualizaLFU(Long conjunto, String Stag) {
        Long acessos = memCache.get(conjunto.intValue()).get(Stag);
        acessos++;
        memCache.get(conjunto.intValue()).put(Stag, acessos);
    }

    protected void substituirLFU(Long conjunto) {
        String lfu = null;
        Long menor = Long.MAX_VALUE;

        for (String key : memCache.get(conjunto.intValue()).keySet()) {
            if (memCache.get(conjunto.intValue()).get(key) == 1L) {
                lfu = key;
                break;
            } else if (memCache.get(conjunto.intValue()).get(key) < menor) {
                menor = memCache.get(conjunto.intValue()).get(key);
                lfu = key;
            }
        }
        if (lfu != null) {
            memCache.get(conjunto.intValue()).remove(lfu);
        }
    }
    
    public void calcbits(){

        if (this.qtdendereco == 1) {
            this.bitsend = 1;
        } else {
            this.bitsend = Math.log(this.qtdendereco) / Math.log(2);
        }
        if (this.pal == 1) {
            this.bitspal = 1;
        } else {
            this.bitspal = Math.log(pal) / Math.log(2);
        }


         this.bitsconjunto = Math.log(this.qtdlinha / this.conjuntoSize) / Math.log(2);
        this.bitstag = this.bitsend - (this.bitspal + this.bitsconjunto);

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


