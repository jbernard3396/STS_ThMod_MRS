package ThMod_FnH.cards.Marisa;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import ThMod_FnH.action.SparkCostAction;
import ThMod_FnH.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class FinalSpark 
	extends CustomCard {
	
	public static final String ID = "FinalSpark";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "img/cards/Strike.png";
	
	private static final int COST = 8;
	private static final int ATK_DMG = 40;
	private static final int UPG_DMG = 10;
	
	public FinalSpark() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.MARISA_COLOR, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.ALL_ENEMY);

		this.isMultiDamage = true;
		this.baseDamage = ATK_DMG;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		this.freeToPlayOnce = true;
		
	    AbstractDungeon.actionManager.addToBottom(
	    		new SFXAction("ATTACK_HEAVY")
	    		);
	    AbstractDungeon.actionManager.addToBottom(
	    		new VFXAction(
	    				p, 
	    				new MindblastEffect(p.dialogX, p.dialogY),
	    				0.1F
	    				)
	    		);
		
	    AbstractDungeon.actionManager.addToBottom(
	    		new DamageAllEnemiesAction(
	    				p,
	    				this.multiDamage, 
	    				this.damageTypeForTurn, 
	    				AbstractGameAction.AttackEffect.SLASH_DIAGONAL
	    				)
	    		);
		AbstractDungeon.actionManager.addToBottom(
				new SparkCostAction()
				);
		if (this.costForTurn > 0) {
			AbstractDungeon.actionManager.addToBottom(
				new GainEnergyAction(-this.costForTurn)
				);
		}
		
		this.upgradeBaseCost(COST);
		this.setCostForTurn(COST);
		this.isCostModified = false;
	}

	public AbstractCard makeCopy() {
		return new FinalSpark();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPG_DMG);
		}
	}
}