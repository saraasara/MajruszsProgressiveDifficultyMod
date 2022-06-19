package com.majruszsdifficulty.config;

import com.majruszsdifficulty.GameState;
import com.mlib.config.ConfigGroup;
import com.mlib.config.DoubleConfig;
import com.mlib.config.IConfigType;
import net.minecraftforge.common.ForgeConfigSpec;

/** Config with double values for each game state. */
public class GameStateDoubleConfig implements IConfigType< Double > {
	protected final ConfigGroup group;
	protected final DoubleConfig normal;
	protected final DoubleConfig expert;
	protected final DoubleConfig master;

	public GameStateDoubleConfig( String name, String comment, double defaultValueNormal, double defaultValueExpert, double defaultValueMaster,
		double minimumValue, double maximumValue
	) {
		this.normal = new DoubleConfig( "normal", "Normal Mode", false, defaultValueNormal, minimumValue, maximumValue );
		this.expert = new DoubleConfig( "expert", "Expert Mode", false, defaultValueExpert, minimumValue, maximumValue );
		this.master = new DoubleConfig( "master", "Master Mode", false, defaultValueMaster, minimumValue, maximumValue );
		this.group = new ConfigGroup( name, comment, this.normal, this.expert, this.master );
	}

	public double getCurrentGameStateValue() {
		return GameState.getCurrentGameStateValue( this.normal.get(), this.expert.get(), this.master.get() );
	}

	@Override
	public void build( ForgeConfigSpec.Builder builder ) {
		this.group.build( builder );
	}

	@Override
	public Double get() {
		return getCurrentGameStateValue();
	}
}
