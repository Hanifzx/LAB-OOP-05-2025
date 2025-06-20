import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

class Typer extends Thread {
    private String botName, wordsTyped;
    private double wpm;
    private TypeRacer typeRacer;

    public Typer(String botName, double wpm, TypeRacer typeRacer) {
        this.botName = botName;
        this.wpm = wpm;
        this.wordsTyped = "";
        this.typeRacer = typeRacer;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public void setWpm(double wpm) {
        this.wpm = wpm;
    }

    public void addWordsTyped(String newWordsTyped) {
        this.wordsTyped += newWordsTyped + " ";
    }

    public String getWordsTyped() {
        return wordsTyped;
    }

    public String getBotName() {
        return botName;
    }

    public double getWpm() {
        return wpm;
    }

    @Override
    public void run() {
        String[] wordsToType = typeRacer.getWordsToType().split(" ");

        // TODO (1)
        // Buatlah variable howLongToType yang memuat waktu yang diperlukan typer
        double howLongToType = 60.0 / wpm;

        // TODO (2)
        // Buatlah perulangan untuk menambahkan kata dengan method
        // addWordToTyped setelah interval waktu sebanyak howLongToType
        for (String word : wordsToType) {
            try {
                Thread.sleep((long) (howLongToType * 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            this.addWordsTyped(word);
        }

        this.addWordsTyped("(Selesai)");

        // TODO (3)
        // Tambahkan typer yang telah selesai mengetik semua kata ke list typeRaceTabel 
        // yang ada di class typeRacer. 
        typeRacer.getRareContestant().remove(this);
        int finishTime = (int) ((System.currentTimeMillis() - typeRacer.startTime) / 1000);
        typeRacer.addResult(new Result(this.botName, finishTime));
    }
}

public class TypeRacer {
    private String wordsToType;
    private ArrayList<Typer> rareContestant = new ArrayList<>();
    private ArrayList<Result> typeRaceTabel = new ArrayList<>();
    public long startTime;

    public String getWordsToType() {
        return wordsToType;
    }

    public ArrayList<Typer> getRareContestant() {
        return rareContestant;
    }

    // Bisa diganti sesuai keinginan masing-masing
    private String[] wordsToTypeList = {
        "Di Bikini Bottom ada Spongebob Squarepants, dia memang keren suka main drumband",
        "Dia jadi koki masaknya krabby patty, menjalani hari hidup bersama Garry",
        "Ayo sama-sama sebutkan nama-nama makhluk dalam sana di Bikini Bottom jaya",
        "Namun ada juga namanya Patrick Star, walau dia cetar tapi hidupnya liar",
        "Tinggal dalam batu tapi suka membantu, sayang hanya satu otaknya itu buntu"
    };

    public void setNewWordsToType() {
        Random random = new Random();
        int angkaRandom = random.nextInt(wordsToTypeList.length);
        wordsToType = wordsToTypeList[angkaRandom];
    }

    // TODO (4)
    // Buat method addResult yang mana digunakan untuk menambahkan typer yang
    // telah selesai (mengetik semua kata), ke dalam list race standing.
    public synchronized void addResult(Result result) {
        typeRaceTabel.add(result);
    }

    private void printRaceStanding() {
        System.out.println("\nKlasemen Akhir Type Racer");
        System.out.println("=========================\n");

        // TODO (5)
        // Tampilkan klasemen akhir dari kompetisi, dengan format
        // {posisi}. {nama} = {waktu penyelesaian dalam detik} detik
        typeRaceTabel.sort(Comparator.comparingInt(Result::getFinishTime));
        int posisi = 1;
        for (Result result : typeRaceTabel) {
            System.out.println(posisi + ". " + result.getName() + " = " + result.getFinishTime() + " detik");
            posisi++;
        }
    }

    public void startRace() {
        // TODO (6)
        // Jalankan kompetisi untuk tiap kontestan
        for (Typer typer : rareContestant) {
            typer.start();
        }
        startTime = System.currentTimeMillis();
    }

    // TODO (7)
    // selama semua peserta belum selesai maka tampilkan typing progress-nya setiap
    // 2 detik, dengan format:
    // Typing Progress ...
    // ===================
    // {nama kontestan} => {text yang telah dia ketik}
    // {nama kontestan} => {text yang telah dia ketik}
    // {nama kontestan} => {text yang telah dia ketik}
    public void displayRaceStandingPeriodically() throws InterruptedException {
        while (true) {
            boolean allDone = true;
            System.out.println("Typing Progress...");
            System.out.println("===================");
            ArrayList<Typer> contestantsCopy = new ArrayList<>(rareContestant); // Salinan untuk aman
            for (Typer typer : contestantsCopy) {
                System.out.println(typer.getBotName() + " => " + typer.getWordsTyped());
                if (!typer.getWordsTyped().contains("(Selesai)")) {
                    allDone = false;
                }
            }
            System.out.println();
            if (allDone) {
                break;
            }
            Thread.sleep(2000);
        }

        // TODO (8)
        // Setelah semua typer selesai, tampilkan race standing setelah semua typer selesai
        printRaceStanding();
    }

    public static void main(String[] args) throws InterruptedException {
        TypeRacer typeRacer = new TypeRacer();
        typeRacer.setNewWordsToType();
        System.out.println("|| Text to Type ||");
        System.out.println("\"" + typeRacer.getWordsToType() + "\"");
        Typer[] typers = new Typer[3];
        typers[0] = new Typer("Bot Spongebob", 50, typeRacer);
        typers[1] = new Typer("Bot Patrick", 72, typeRacer);
        typers[2] = new Typer("Bot Squidward", 80, typeRacer);
        typeRacer.getRareContestant().addAll(Arrays.asList(typers));
        typeRacer.startRace();
        typeRacer.displayRaceStandingPeriodically();
    }
}

class Result {
    private String name;
    private int finishTime;

    public Result(String name, int finishTime) {
        this.name = name;
        this.finishTime = finishTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
}
