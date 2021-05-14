package edu.iis.mto.oven;

import static edu.iis.mto.oven.HeatType.*;
import static org.hamcrest.Matchers.equalTo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OvenTest {

    private Oven oven;
    @Mock
    private HeatingModule heatingModule;
    @Mock
    private Fan fan;
    List<ProgramStage> stages= new ArrayList<>();

    @BeforeEach
    void setUp(){
        oven = new Oven(heatingModule,fan);
        ProgramStage programStage2= ProgramStage.builder().withTargetTemp(80).withStageTime(60).withHeat(HEATER).build();
        ProgramStage programStage1= ProgramStage.builder().withTargetTemp(100).withStageTime(30).withHeat(THERMO_CIRCULATION).build();
        ProgramStage programStage3= ProgramStage.builder().withTargetTemp(120).withStageTime(40).withHeat(GRILL).build();
        stages.add(programStage1);
        stages.add(programStage2);
        stages.add(programStage3);
    }
    @Test
    void itCompiles() {
        MatcherAssert.assertThat(true, equalTo(true));
    }

    @Test
    void checkIfMethodWillInitializeTheTemperature(){
        BakingProgram program = BakingProgram.builder().withInitialTemp(80).withStages(stages).build();

        oven.start(program);
        HeatingSettings settings= HeatingSettings.builder().withTargetTemp(80).withTimeInMinutes(60).build();
        verify(heatingModule).heater(settings);
    }

    @Test
    void checkIfRunStageThrowsExceptionWhenTermalCircuit() throws HeatingException {
        BakingProgram program = BakingProgram.builder().withInitialTemp(80).withStages(stages).build();
        HeatingSettings settings= HeatingSettings.builder().withTargetTemp(100).withTimeInMinutes(30).build();
        doThrow(HeatingException.class).when(heatingModule).termalCircuit(settings);

        assertThrows(OvenException.class,()->oven.start(program));
    }

    @Test
    void checkIfRunStageThrowsExceptionWhenGrill() throws HeatingException {
        BakingProgram program = BakingProgram.builder().withInitialTemp(80).withStages(stages).build();
        HeatingSettings settings= HeatingSettings.builder().withTargetTemp(120).withTimeInMinutes(40).build();
        doThrow(HeatingException.class).when(heatingModule).grill(settings);

        assertThrows(OvenException.class,()->oven.start(program));
    }

    @Test
    void checkIfRunStahrowsExceptionWhenGrill() throws HeatingException {
        BakingProgram program = BakingProgram.builder().withInitialTemp(80).withStages(stages).build();
        HeatingSettings settings= HeatingSettings.builder().withTargetTemp(120).withTimeInMinutes(40).build();
        doThrow(HeatingException.class).when(heatingModule).grill(settings);

        assertThrows(OvenException.class,()->oven.start(program));
    }


}
