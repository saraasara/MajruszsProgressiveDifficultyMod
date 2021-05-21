package com.majruszs_difficulty.events;

import com.majruszs_difficulty.GameState;
import com.mlib.WorldHelper;
import com.mlib.config.DoubleConfig;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nullable;

/** Class representing base feature that depends on chance and can be disabled. */
public abstract class ChanceFeatureBase extends FeatureBase {
	protected final boolean shouldChanceBeMultipliedByCRD; // CRD = Clamped Regional Difficulty
	protected final DoubleConfig chance;

	public ChanceFeatureBase( String configName, String configComment, double defaultChance, GameState.State minimumState,
		boolean shouldChanceBeMultipliedByCRD
	) {
		super( configName, configComment, minimumState );
		this.shouldChanceBeMultipliedByCRD = shouldChanceBeMultipliedByCRD;

		String crd = "(this value is scaled by Clamped Regional Difficulty)";
		String chanceComment = "Chance of this feature to happen. " + ( this.shouldChanceBeMultipliedByCRD ? crd : "" );
		this.chance = new DoubleConfig( "chance", chanceComment, false, defaultChance, 0.0, 1.0 );

		this.featureGroup.addConfigs( this.chance );
	}

	/** Returns chance of applying event on entity. */
	protected double getChance() {
		return this.chance.get();
	}

	/** Calculating final chance. (after applying clamped regional difficulty if needed) */
	protected double calculateChance( @Nullable LivingEntity target ) {
		double clampedRegionalDifficulty = target != null ? WorldHelper.getClampedRegionalDifficulty( target ) : 0.25;

		return getChance() * ( this.shouldChanceBeMultipliedByCRD ? clampedRegionalDifficulty : 1.0 );
	}
}
