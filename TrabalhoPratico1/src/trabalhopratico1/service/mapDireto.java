package service;

import java.util.ArrayList;
import model.FileManager;
import model.mapeamento;


public class mapDireto extends mapeamento {
    String caminhoteste;

    public mapDireto(String caminhoteste,String config) {
        super(config);
        this.config = config;
        this.caminhoteste = caminhoteste;
    }

    public void enderecamentoDireto() {
        
        setTam();
        calcbits();
        iniciarVariaveis();

        String vetCache[] = new String[this.qtdlinha.intValue()];
        ArrayList<String> teste = FileManager.stringReader(caminhoteste);
        for (String linha : teste) {
            //System.out.println(linha);
            Long acesso = Long.parseLong(linha);
            String stringBin = intToBinaryString(acesso, (int)this.bitsend);
            String strlin = stringBin.substring((int)this.bitstag , (((int)this.bitsend-(int)this.bitspal)));
            Long lin = binaryToInt(strlin, ((int)this.bitslinha));
            String tag = stringBin.substring(0,((int)this.bitstag));            

            if(vetCache[lin.intValue()] == (null)){
                miss++;
             // System.out.println("Erro");
                vetCache[lin.intValue()] = tag;

            }
            else if (vetCache[lin.intValue()].equals(tag)){
                hit++;
             //System.out.println("Acerto");
                
            }else {
                miss++;
                //System.out.println("Erro");
                vetCache[lin.intValue()] = tag;

            }
            this.taxahit = (double)hit / (double)teste.size();
            this.taxaerro = (double)miss / (double)teste.size();
            setarValoresFinais(this.hit,this.miss,this.taxahit,this.taxaerro);

          //System.out.println(stringBin);
        }

    }
        public void setarValoresFinais(Long hit, Long miss, double taxahit, double taxaerro){
        setTaxaerro(this.taxaerro*100);
        setTaxahit(this.taxahit*100);
        setHit(this.hit);
        setMiss(this.miss);
        
    }
        public void calcbits(){
         this.bitsend = Math.log(qtdendereco)/Math.log(2);//qtd de bits pra representar o endereço
         this.bitslinha = Math.log(qtdlinha)/Math.log(2);
        if (this.pal==1){
            this.bitspal = 1;
        }else {
           this.bitspal = Math.log(pal)/Math.log(2);
        }

        this.bitstag = this.bitsend - (this.bitslinha + this.bitspal);
        }
        
        public void iniciarVariaveis(){
        this.taxahit = 0;
        this.taxaerro = 0;
        this.miss = 0L;
        this.hit = 0L;
    }
    }



