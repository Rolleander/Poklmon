package com.broll.poklmon.menu.state.sites;

import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.attack.AttackType;
import com.broll.pokllib.poklmon.ElementType;
import com.broll.pokllib.poklmon.Poklmon;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.menu.MenuUtils;
import com.broll.poklmon.menu.state.StateSite;
import com.broll.poklmon.resource.GUIFonts;
import com.broll.poklmon.save.AttackData;
import com.broll.poklmon.save.PoklmonData;

public class AttackSite extends StateSite {

	public AttackSite(Poklmon poklmonInfo, PoklmonData poklmon, DataContainer data) {
		super(poklmonInfo, poklmon, data);

	}

	private Attack[] attacks;
	private int ap[];

	@Override
	protected void initData() {

		attacks = new Attack[4];
		ap = new int[4];

		for (int i = 0; i < 4; i++) {
			AttackData ad = poklmon.getAttacks()[i];
			int nr = ad.getAttack();
			ap[i] = ad.getAp();
			if (nr != AttackData.NO_ATTACK) {
				attacks[i] = data.getAttacks().getAttack(nr);
			}
		}
	}

	@Override
	public void render(Graphics g, float x, float y) {

		this.x = x;
		this.y = y + 5;

		for (int i = 0; i < 4; i++) {
			renderAttack(g, i);
			this.y += 108;
		}

	}

	private void renderAttack(Graphics g, int nr) {

		float x = this.x;
		float y = this.y;
		Attack attack = attacks[nr];
		int ap = this.ap[nr];
		if (attack != null) {
			// background
			g.setColor(ColorUtil.newColor(0, 0, 0, 50));
			g.fillRect(x - 2, y - 6, 600, 36);

			ElementType element = attack.getElementType();
			String name = attack.getName();
			int maxap = attack.getDamage().getAp();

			Image el = data.getGraphics().getMenuGraphicsContainer().getElements().getSprite(0, element.ordinal());
			el.draw(x, y);

			g.setFont(GUIFonts.dialogText);
			g.setColor(ColorUtil.newColor(250, 250, 250));
			MenuUtils.drawFancyString(g, name, x + 67, y - 13);

			g.setFont(GUIFonts.smallText);

			if (((float) ap) / ((float) maxap) <= 0.2) {
				g.setColor(ColorUtil.newColor(150, 20, 20));
			} else {
				g.setColor(ColorUtil.newColor(250, 250, 250));

			}
			String aptext = "AP: " + ap + " / " + maxap;
			g.drawString(aptext, x + 340, y);
			g.setColor(ColorUtil.newColor(250, 250, 250));

			y += 34;

			AttackType type = attack.getAttackType();

			Image ty = data.getGraphics().getMenuGraphicsContainer().getAttacktypes().getSprite(type.ordinal(), 0);
			ty.draw(x, y);

			int damage = attack.getDamage().getDamage();
			g.drawString("Schaden: ", x + 70, y - 5);
			g.drawString("Genauigkeit: ", x + 220, y - 5);

			String damageText = "-";
			if (damage > 0 && type != AttackType.STATUS) {
				damageText = "" + damage;
			}

			int genaui = (int) (attack.getDamage().getHitchance() * 100);

			g.setColor(ColorUtil.newColor(250, 100, 0));
			MenuUtils.drawFancyString(g, damageText, x + 165, y - 5);
			g.setColor(ColorUtil.newColor(100, 100, 100));
			MenuUtils.drawFancyString(g, genaui + "%", x + 345, y - 5);

			g.setColor(ColorUtil.newColor(50, 50, 50));
			y += 23;

			MenuUtils.drawBoxString(g, attack.getDescription(), fontUtils,x, y - 5, 780, -4);
			// g.drawString(attack.getDescription(), x, y - 5);

		}
	}
}
