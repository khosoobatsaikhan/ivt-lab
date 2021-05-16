package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore firstTorpedo;
  private TorpedoStore secondTorpedo;

  @BeforeEach
  public void init() {
    firstTorpedo = mock(TorpedoStore.class);
    secondTorpedo = mock(TorpedoStore.class);
    this.ship = new GT4500(firstTorpedo, secondTorpedo);
  }

  @Test
  public void fireTorpedo_Single_Success() {
    // Arrange
    when(firstTorpedo.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    // Verifying the mock
    verify(firstTorpedo, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success() {
    // Arrange
    when(firstTorpedo.fire(1)).thenReturn(true);
    when(secondTorpedo.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    // Verifying the mock
    verify(firstTorpedo, times(1)).fire(1);
    verify(secondTorpedo, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_AllTorpedoEmpty() {
    // Arrange
    when(firstTorpedo.isEmpty()).thenReturn(true);
    when(secondTorpedo.isEmpty()).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    // Verifying the mock
    verify(firstTorpedo, times(1)).isEmpty();
    verify(secondTorpedo, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_FirstTorpedoEmpty() {
    // Arrange
    when(firstTorpedo.isEmpty()).thenReturn(true);
    when(secondTorpedo.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    // Verifying the mock
    verify(firstTorpedo, times(1)).isEmpty();
    verify(secondTorpedo, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_FireAllFirstTorpedoEmpty() {
    // Arrange
    when(firstTorpedo.isEmpty()).thenReturn(true);
    when(secondTorpedo.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    // Verifying the mock
    verify(secondTorpedo, times(1)).fire(1);
    verify(firstTorpedo, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_PrimaryFireTwiceCheck() {
    // Arrange
    when(firstTorpedo.isEmpty()).thenReturn(false);
    when(secondTorpedo.isEmpty()).thenReturn(true);
    when(firstTorpedo.fire(1)).thenReturn(true);
    when(secondTorpedo.fire(1)).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    // Assert
    assertEquals(true, result);
    // Verifying the mock
    verify(secondTorpedo, times(1)).isEmpty();
    verify(firstTorpedo, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_checkPrimaryFiredLast() {
    // Arrange
    when(firstTorpedo.isEmpty()).thenReturn(false);
    when(secondTorpedo.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.ALL);
    boolean result = ship.isPrimaryFiredLast();
    // Assert
    assertEquals(true, result);
    // Verifying the mock
    verify(secondTorpedo, times(1)).isEmpty();
    verify(firstTorpedo, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_PrimaryFireLastTest() {
    // Arrange
    when(firstTorpedo.fire(1)).thenReturn(true);
    when(secondTorpedo.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    // Assert
    assertEquals(true, result);
    // Verifying the mock
    verify(secondTorpedo, times(1)).fire(1);
    verify(firstTorpedo, times(1)).fire(1);
  }
}
