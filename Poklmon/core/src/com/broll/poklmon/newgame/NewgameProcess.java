package com.broll.poklmon.newgame;

import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.data.SoundContainer;
import com.broll.poklmon.game.GameDifficulty;
import com.broll.poklmon.script.ScriptProcessingRunnable;

public class NewgameProcess extends ScriptProcessingRunnable {

	private NewgameListener newgameListener;
	private NewgameGUI gui;
	private int selection;
	private String input;

	public NewgameProcess(NewgameListener listener) {
		this.newgameListener = listener;
	}

	public void setGui(NewgameGUI gui) {
		this.gui = gui;
	}

	@Override
	public void runProcess() {

		String myName = null;
		String poklmonName = null;
		int poklmonID = 0;
		int character = 0;
		int difficulty = 0;
		// start new game process

		showText("Hallo neuer Trainer!");
		String[] pokls = { "Brotzel", "Otamu", "Wurzgel" };
		String[] balls = { "Roter Ball", "Blauer Ball", "Grüner Ball" };
		String[] type = { "Feuer", "Wasser", "Pflanzen" };
		int[] ids = { 4, 16, 15 };
		if (!PoklmonGame.DEBUG_MODE) {
			showText("Mein Name ist Professor Pfiffikus!");
			showSelection("Du weißt doch sicher was Poklmon sind?", new String[] { "Klar doch!", "Wie meinen?" });
			if (selection == 0) {
				showText("Tu doch nicht so du Hobo!");
				showText("Mein Geschwätz musst du dir anhören, egal was du sagst.");
			} else {
				showText("Naja ich wollte es dir eh gerade erklären.");
			}

			gui.getEventDisplay().showPokmlon(1);
			showText("Kuckst du hier, die Viecher nennt man Poklmon!");
			showText("In dieser Welt wimmelt es von ihnen! Manche können fliegen, andere sind einfach nur hässlich.");
			showText("Das Beste ist, DU darfst mir alle Poklmon bringen!");
			showText("Was? Du fragst wieso?");
			showText("Ähh... ich bin doch Professor. Ich will jedes einzelne von ihnen... ehm... untersuchen!");
			showText("Und für diese Aufgabe scheinst du mir der richtige Lümmel zu sein!");
			showText("Du siehst schon so aus als hättest du keine Hobbies...");
			showText("Außerdem bekommst du von anderen Poklmon-Sammlern Geld, wenn du so ein schwules Kämpfchen gewinnst!");
			showText("Ah du bist einfach wie geschaffen für diesen Job! Deinem Gesichtsausdruck nach zu urteilen, möchtest du das sogar alles unbezahlt machen!");
			showText("Oh man die heutige Jugend ist echt blöd...");
			showText("Ah ja fast hätte ich es vergessen!");
			showText("Ich erkenne dich nicht so richtig, ich meine ich bin mir nicht sicher...");
			showText("... bist du ein Junge, oder nur ein hässliches Mädchen das aussieht wie einer?");

			showSelection("Kläre mich bitte auf, bevor ich Augenkrebs bekomme!", new String[] { "Bube", "Lady" });
			if (selection == 0) {
				showText("Ah tatsächlich! Besonders schön bist du ja trotzdem nicht...");
				character = 0;
			} else {
				showText("Oh das tut mir leid für dich...");
				showText("Naja seis drum!");
				character = 1;
			}

			showSelection("Ach und nebenbei, möchtest du mir verraten wie du eigentlich heißt?", new String[] {
					"Von mir aus", "Nö!" });
			if (selection == 0) {
				boolean hasname = false;
				do {
					showText("Na dann schieß mal los!");
					openNameInput("Hans");
					showText("Ha ha ha! Ich weiß nich ob ich lachen oder weinen soll bei deinem Namen!");
					showSelection("Heißt du wirklich " + input + "?", new String[] { "Ja leider", "Doch nicht" });
					if (selection == 0) {
						showText("Junge junge, das wird ja immer besser!");
						myName = input;
						hasname = true;
					} else {
						showText("Versuchs doch nochmal!");
					}
				} while (hasname == false);
			} else {
				showText("Ah du bist also Arschclown!");
				myName = "Arschclown";
			}

			showText("Soso dann weiß ich ja jetzt wer hier vor mir sitzt...");
			showText("Äh? Was gibt denn das jetzt?!");
			showText("He! Halt Stopp du darfst noch nicht gehen!");
			showText("Ich wollte mit dir noch ein bisschen über Poklmon quatschen!");
			showSelection("Bleib doch noch ein bisschen hier!", new String[] { "Gerne doch!" });
			showSelection("Bist du dir ganz sicher, dass du nicht gehen möchtest?", new String[] {
					"Ach ist doch kein Problem!", "Erzähl nur weiter!" });
			showText("Hehe der Trick funktioniert immer!");

			String p = "...";
			for (int i = 0; i < 10; i++) {
				showText("Ähhm" + p);
				p += ".";
			}
			showText("...ach nichts!");
			showText("Ah ja! Ich wollte dir ja noch etwas erzählen!");
			int count = gui.getData().getPoklmons().getNumberOfPoklmons();
			showText("Du willst sicher wissen wie viele Poklmon es gibt?");
			showText("Lass mich mal kurz durchrechnen...");
			showText("...hmmm");
			showText("...genau");
			showText("...das auch noch");
			showText("...den da");
			showText("...mal zwei");
			showText("...keinen vergessen");
			showText("...ok ich habs!");
			showText("...naja ich schau doch nochmal lieber in meinem Computer nach!");
			showText("So... muss nur noch hochfahren die Kiste...");
			showText("...Biep!");
			showText("BISHER GESICHTETE POKLMON: " + count);
			showText("Genau so ähnlich habe ich es auch berechnet!");
			showText("Also gut, dann fehlt dir jetzt nur noch das hier...");
			giveItem("Pokldex");
			showText("Damit kannst du immer nachschauen welche Poklmon du gesehen oder bereits gefangen hast. Außerdem stehen dort noch weitere Informationen, sobald du eine bestimmte Art gefangen hast.");

			showText("Hast du eigentlich schon bemerkt dass wir uns in einem virtuellen Spiel befinden? Ich kann dir auch nicht sagen warum, aber deshalb darfst du die Schwierigkeit deines Abenteuers selbst bestimmen!");
			showSelection("Wie schwer soll dieses Spiel werden?", GameDifficulty.NAMES);
			difficulty = selection;
			switch (difficulty){
				case GameDifficulty.DIFFICULTY_NORMAL:
					showText("Na gut, das war ja zu erwarten.");
					break;
				case GameDifficulty.DIFFICULTY_EASY:
					showText("Du willst dich wohl überhaupt nicht anstrengen müssen? Das habe ich mir gleich gdacht...");
					break;
				case GameDifficulty.DIFFICULTY_HARD:
					showText("Ich muss schon sagen, das ist mutig von dir.");
					break;
				case GameDifficulty.DIFFICULTY_BRUTAL:
					showText("Scheinbar bist du grössenwahnsinnig oder einfach nur dumm, sage nicht ich habe dich nicht gewarnt!");
					break;
			}
			showText("Jetzt fehlt dir nur noch dein erstes eigenes Poklmon!");
			showText("Da du eh keine Ahnung hast, nimm einfach einen der drei Poklbälle hier!");
			boolean hasPoklmon = false;
			do {
				showSelection("Wähle einen Poklball aus!", new String[] { balls[0], balls[1], balls[2],
						"Ich will nicht!" });
				if (selection != 3) {
					String pokl = pokls[selection];
					String t = type[selection];
					String ball = balls[selection];
					int sel = selection;
					gui.getEventDisplay().showPokmlon(ids[sel]);
					showText("Aha! In diesem Ball befindet sich das " + t + "-Poklmon " + pokl + "!");
					showSelection("Bist du dir sicher, dass du " + pokl + " haben möchtest?", new String[] { "Ja!",
							"Eher nicht" });
					if (selection == 0) {
						giveItem(ball);
						hasPoklmon = true;
						showText(t
								+ "-Poklmon finde ich persönlich ja nicht besser als altes Klopapier. Naja du musst ja damit auskommen und nicht ich!");
						poklmonID = ids[sel];
					} else {
						showText("Dann such dir halt ein anderes aus, aber lass dir nicht den ganzen Tag Zeit!");
					}
				} else {
					showText("Jetzt pass mal auf! Willst du das ich den ganzen Käse nochmal erzähle oder nimmst du jetzt endlich mein Geschenk an?");
					showText("Echt unglaublich sowas...");
					showText("Jetzt nimm schon einen der Bälle du Sack!");
				}
			} while (hasPoklmon == false);

			showText("..so die anderen Beiden kann ich jetzt zum Pokl-Schlachter bringen, die will eh keiner mehr.");
			showText("Dann haben wir ja jetzt alles geklärt.");
			showText("Also dann, hoffentlich bis bald!");
			showText("...und vergiss nicht ab und zu vorbeizukommen um mit mir zu reden!");
			showText("Ich werde dir zwar nichts mehr schenken glaube ich, aber ich bin nicht umsonst Professor Poklmon!");
			showText("Ja und? Ich weiß dass ich Professor Pfiffikus heiße, aber das ist mein zurecht verdienter Titel, denn bald werde ich diese Viecher ja ganz genau unter die Lupe nehmen!");
			showText("Und jetzt beweg deinen süßen kleinen Arsch nach draußen!");
			showText("Ah Halt Stopp! Mir ist noch etwas eingefallen!");
			showText("Wenn du möchtest kannst du deinem Poklmon einen eigenen Namen geben!");
			showText("Ich weiß das klingt bescheuert, aber manchen Menschen macht das halt Spaß.");

			showSelection("Willst du deinem Poklmon einen eigenen Namen geben?", new String[] { "Sehr gerne!",
					"Lieber nicht", "Was?" });
			if (selection == 0) {
				showText("Oh Gott dann gib ihm halt einen Namen!");
				openNameInput("Peter");
				poklmonName = input;
				showText("Wenigstens ist " + input + " genauso bescheuert wie das Poklmon selbst.");
			} else if (selection == 1) {
				showText("Bist wohl nicht so kreativ, was?");
			} else {
				showText("...na gut dann geb ich ihm halt einen Namen!");
				poklmonName = "Arschclown";
			}
			showText("...");
			showText("...nun gut, das wärs dann aber wirklich erstmal.");
			showText("Gehab dich wohl " + myName + "!");
		} else {
			// just for debug
			showSelection("Boy or Girl", new String[] { "Boi", "Grill" });
			character = selection;
			showText("Dein name");
			openNameInput("Hans");
			myName = input;
			showSelection("Wähle einen Poklball aus!", new String[] { balls[0], balls[1], balls[2] });
			poklmonID = ids[selection];
			showText("poke name");
			openNameInput("Peter");
			poklmonName = input;
		}
		newgameListener.finishedSelection(difficulty, character, myName, poklmonID, poklmonName);
	}

	private void playSound(String name) {
		SoundContainer sounds = gui.getData().getSounds();
		sounds.playSound(name);
	}

	private void giveItem(String name) {
		gui.showText(name + " erhalten!");
		gui.setTextEnd();
		playSound("item_get");
		waitForResume();
	}

	public void showText(String text) {
		gui.showText(text);
		waitForResume();
	}

	public void openNameInput(String defaultvalue) {
		gui.openNameInput(defaultvalue);
		waitForResume();
	}

	public int showSelection(String text, String[] items) {
		gui.showText(text);
		waitForResume();
		gui.showInfo(text);
		gui.showSelection(items);

		waitForResume();
		return selection;
	}

	public synchronized void resume() {
		notify();
	}

	public synchronized void waitForResume() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setSelectedAnswer(int item) {
		this.selection = item;
	}

	public void setInputName(String name) {
		this.input = name;
	}

}
