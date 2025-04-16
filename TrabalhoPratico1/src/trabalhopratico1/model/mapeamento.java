package model;
import java.util.ArrayList;


public class mapeamento {

    protected Long miss = 0L;
    protected Long hit = 0L;
    protected double taxahit;
    protected double taxaerro;
    protected Long memsize;
    protected Long pal;
    protected Long cache;
    protected Long Linha;
    protected Long qtdlinha;
    protected Long qtdendereco;
    protected String config;
    protected double bitsend;
    protected double bitspal;
    protected double bitstag;
    protected double bitslinha;
    protected double bitsconjunto;
    
    public mapeamento(String config) {
        this.config = config;
    }

    public void setTam(){
        memsize = calctam(0,config);
        pal = calctam(1,config);
        cache = calctam(2,config);
        Linha = calctam(3,config)*pal;

        this.qtdlinha = cache/Linha; //calcula a qtd de linha na cache
        this.qtdendereco = memsize/pal; //calcula o tamanho da memoria
    }
    public double getBitsPal(){
        return bitspal;
    }
    public void setBitsPal(double bitspal){
        this.bitspal = bitspal;
    }

    public double getBitsend(){
        return bitsend;
    }
    public void setBitsEnd(double bitsend){
        this.bitsend = bitsend;
    }
    public double getBitsTag(){
        return bitstag;
    }
    public void setBitsTag(double bitstag){
        this.bitstag = bitstag;
    }
    public String getConfig(){
    return config;
    }
    
    public void setConfig(String config){
    this.config = config;
    }


    public Long getMiss() {
        return miss;
    }

    public void setMiss(Long miss) {
        this.miss = miss;
    }

    public Long getHit() {
        return hit;
    }

    public void setHit(Long hit) {
        this.hit = hit;
    }

    public Long getMemsize() {
        return memsize;
    }

    public void setMemsize(Long memsize) {
        this.memsize = memsize;
    }

    public Long getPal() {
        return pal;
    }

    public void setPal(Long pal) {
        this.pal = pal;
    }

    public Long getCache() {
        return cache;
    }

    public void setCache(Long cache) {
        this.cache = cache;
    }

    public Long getLinha() {
        return Linha;
    }

    public void setLinha(Long linha) {
        Linha = linha;
    }

    public Long getQtdlinha() {
        return qtdlinha;
    }

    public void setQtdlinha(Long qtdlinha) {
        this.qtdlinha = qtdlinha;
    }

    public Long getQtdendereco() {
        return qtdendereco;
    }

    public void setQtdendereco(Long qtdendereco) {
        this.qtdendereco = qtdendereco;
    }

    public static Long[] intToBinary(Long value, int size) {
        if (value > Math.pow(2, size) - 1) {
            return null;
        }
        Long bin[] = new Long[size];
        Long i = 0L;
        while (value > 0 && i < size) {
            Long num = value % 2;
            value = value / 2;
            bin[i.intValue()] = num;
            i++;
        }
        for (Long j = 0L; j <= size / 2; j++) {
            Long temp = bin[j.intValue()];
            bin[j.intValue()] = bin[size - j.intValue() - 1];
            bin[size - j.intValue() - 1] = temp;
        }
        return bin;
    }

    public static String intToBinaryString(Long value, int size) {
        if (value > Math.pow(2, size) - 1) {
            return null;
        }
        char bin[] = new char[size];
        for (Long i = 0L; i < size; i++) {
            bin[i.intValue()] = '0';
        }
        int i = 0;
        while (value > 0 && i < size) {
            Long num = value % 2;
            value = value / 2;
            bin[i] = (num + "").charAt(0);
            i++;
        }
        for (Long j = 0L; j <= size / 2; j++) {
            char temp = bin[j.intValue()];
            bin[j.intValue()] = bin[size - j.intValue() - 1];
            bin[size - j.intValue() - 1] = temp;
        }
        String nova = new String(bin);
        return nova;
    }

    public static Long binaryToInt(String linha,int tam){
        double inteiro = 0;
        for (int i=((tam)-1), j=0; i>=0;i--,j++){
           if (linha.charAt(i) == '0'){
            inteiro += (Math.pow(2,(j))*0);

           }else if(linha.charAt(i) == '1'){
            inteiro += (Math.pow(2,(j))*1);
           }
        }
        Long inteiro2 = (long)inteiro;

     return inteiro2;

    }

    public static Long calctam (int index,String config){ //metodo para separar antes e depois do igual , para calcular o tamanho da cache, linha , palavra e linha.

        ArrayList<String> dadosConfig = FileManager.stringReader(config);
        String dadosCompleto = dadosConfig.get(index);
        String partes[] = dadosCompleto.split("=");
       /*  System.out.println(partes[0]);
        System.out.println(partes[1]);*/
        String partesDado[] = partes[1].split(" ");

        Long tam = 0L;

        if(partesDado[2].equals("B;") || partesDado[2].equals("pal;")){
        //    tam = Integer.parseInt(partesDado[1]);
        tam = Long.parseLong(partesDado[1]);
        }
        else if (partesDado[2].equals("KB;")){
             tam = Long.parseLong(partesDado[1])*1024;

        }else if (partesDado[2].equals("MB;")){
             tam = Long.parseLong(partesDado[1])*1024*1024;

        }else if (partesDado[2].equals("GB;")){
             tam = Long.parseLong(partesDado[1])*1024*1024*1024;

        }

        return tam;

    

    }

    public double getTaxahit() {
        return taxahit;
    }

    public void setTaxahit(double taxahit) {
        this.taxahit = taxahit;
    }

    public double getTaxaerro() {
        return taxaerro;
    }

    public void setTaxaerro(double taxaerro) {
        this.taxaerro = taxaerro;
    }
    public double getBitsLinha() {
        return taxaerro;
    }

    public void setBitsLinha(double bitslinha) {
        this.bitslinha = bitslinha;
    }
     public double getBitsConjunto() {
        return bitsconjunto;
    }

    public void setBitsConjunto(double bitsconjunto) {
        this.bitsconjunto = bitsconjunto;
    }


    

    

}
