/*
 * Copyright 2012 Sony Computer Science Laboratories, Inc. <info@kadecot.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sonycsl.echo.eoj.device.housingfacilities;

import com.sonycsl.echo.EchoFrame;
import com.sonycsl.echo.eoj.EchoObject;
import com.sonycsl.echo.eoj.device.DeviceObject;

public abstract class ElectricallyOperatedShade extends DeviceObject {
	@SuppressWarnings("unused")
	private static final String TAG = ElectricallyOperatedShade.class.getSimpleName();
	
	public static final byte CLASS_GROUP_CODE = (byte)0x02;
	public static final byte CLASS_CODE = (byte)0x60;

	protected static final byte EPC_OPEN_CLOSE_SETTING = (byte)0xE0;
	protected static final byte EPC_DEGREE_OF_OPENI_NG_LEVEL = (byte)0xE1;
	protected static final byte EPC_SET_VALUE_OF_SHADE_ANGLE = (byte)0xE2;
	protected static final byte EPC_SHADE_OPEN_CLOSE_SPEED = (byte)0xE3;

	@Override
	public byte getClassGroupCode() {
		return CLASS_GROUP_CODE;
	}

	@Override
	public byte getClassCode() {
		return CLASS_CODE;
	}

	/**
	 * Open/close<br>Open = 0x41, close = 0x42
	 */
	protected abstract boolean setOpenCloseSetting(byte[] edt);
	/**
	 * Open/close<br>Open = 0x41, close = 0x42
	 */
	protected abstract byte[] getOpenCloseSetting();
	/**
	 * Used to specify the Degree-of-opening level by selecting a level from among the 8 predefined levels, and to acquire the current setting.<br>0x31 to 0x38
	 */
	protected boolean setDegreeOfOpeniNgLevel(byte[] edt) {return false;}
	/**
	 * Used to specify the Degree-of-opening level by selecting a level from among the 8 predefined levels, and to acquire the current setting.<br>0x31 to 0x38
	 */
	protected byte[] getDegreeOfOpeniNgLevel() {return null;}
	/**
	 * Shade angle value<br>0x00.0xB4 (0.180. )
	 */
	protected boolean setSetValueOfShadeAngle(byte[] edt) {return false;}
	/**
	 * Shade angle value<br>0x00.0xB4 (0.180. )
	 */
	protected byte[] getSetValueOfShadeAngle() {return null;}
	/**
	 * Low/Medium/High<br>Low = 0x41, Medium = 0x42, High = 0x43
	 */
	protected boolean setShadeOpenCloseSpeed(byte[] edt) {return false;}
	/**
	 * Low/Medium/High<br>Low = 0x41, Medium = 0x42, High = 0x43
	 */
	protected byte[] getShadeOpenCloseSpeed() {return null;}


	@Override
	protected void onReceiveSet(EchoFrame res, byte epc, byte pdc, byte[] edt) {
		super.onReceiveSet(res, epc, pdc, edt);
		switch(epc) {
		case EPC_OPEN_CLOSE_SETTING:
			res.addProperty(epc, edt, setOpenCloseSetting(edt));
			break;
		case EPC_DEGREE_OF_OPENI_NG_LEVEL:
			res.addProperty(epc, edt, setDegreeOfOpeniNgLevel(edt));
			break;
		case EPC_SET_VALUE_OF_SHADE_ANGLE:
			res.addProperty(epc, edt, setSetValueOfShadeAngle(edt));
			break;
		case EPC_SHADE_OPEN_CLOSE_SPEED:
			res.addProperty(epc, edt, setShadeOpenCloseSpeed(edt));
			break;

		}
	}

	@Override
	protected void onReceiveGet(EchoFrame res, byte epc) {
		super.onReceiveGet(res, epc);
		byte[] edt;
		switch(epc) {
		case EPC_OPEN_CLOSE_SETTING:
			edt = getOpenCloseSetting();
			res.addProperty(epc, edt, (edt != null && (edt.length == 1)));
			break;
		case EPC_DEGREE_OF_OPENI_NG_LEVEL:
			edt = getDegreeOfOpeniNgLevel();
			res.addProperty(epc, edt, (edt != null && (edt.length == 1)));
			break;
		case EPC_SET_VALUE_OF_SHADE_ANGLE:
			edt = getSetValueOfShadeAngle();
			res.addProperty(epc, edt, (edt != null && (edt.length == 1)));
			break;
		case EPC_SHADE_OPEN_CLOSE_SPEED:
			edt = getShadeOpenCloseSpeed();
			res.addProperty(epc, edt, (edt != null && (edt.length == 1)));
			break;

		}
	}
	
	@Override
	public Setter set() {
		return new SetterImpl(ESV_SET_NO_RES);
	}

	@Override
	public Setter setC() {
		return new SetterImpl(ESV_SET_RES);
	}

	@Override
	public Getter get() {
		return new GetterImpl();
	}

	@Override
	public Informer inform() {
		return new InformerImpl();
	}
	
	public static class Receiver extends DeviceObject.Receiver {

		@Override
		protected void onReceiveSetRes(EchoObject eoj, short tid, byte epc,
				byte pdc, byte[] edt) {
			super.onReceiveSetRes(eoj, tid, epc, pdc, edt);
			switch(epc) {
			case EPC_OPEN_CLOSE_SETTING:
				onSetOpenCloseSetting(eoj, tid, (pdc != 0));
				break;
			case EPC_DEGREE_OF_OPENI_NG_LEVEL:
				onSetDegreeOfOpeniNgLevel(eoj, tid, (pdc != 0));
				break;
			case EPC_SET_VALUE_OF_SHADE_ANGLE:
				onSetSetValueOfShadeAngle(eoj, tid, (pdc != 0));
				break;
			case EPC_SHADE_OPEN_CLOSE_SPEED:
				onSetShadeOpenCloseSpeed(eoj, tid, (pdc != 0));
				break;

			}
		}

		@Override
		protected void onReceiveGetRes(EchoObject eoj, short tid, byte epc,
				byte pdc, byte[] edt) {
			super.onReceiveGetRes(eoj, tid, epc, pdc, edt);
			switch(epc) {
			case EPC_OPEN_CLOSE_SETTING:
				onGetOpenCloseSetting(eoj, tid, pdc, edt);
				break;
			case EPC_DEGREE_OF_OPENI_NG_LEVEL:
				onGetDegreeOfOpeniNgLevel(eoj, tid, pdc, edt);
				break;
			case EPC_SET_VALUE_OF_SHADE_ANGLE:
				onGetSetValueOfShadeAngle(eoj, tid, pdc, edt);
				break;
			case EPC_SHADE_OPEN_CLOSE_SPEED:
				onGetShadeOpenCloseSpeed(eoj, tid, pdc, edt);
				break;

			}
		}
		
		/**
		 * Open/close<br>Open = 0x41, close = 0x42
		 */
		protected void onSetOpenCloseSetting(EchoObject eoj, short tid, boolean success) {}
		/**
		 * Open/close<br>Open = 0x41, close = 0x42
		 */
		protected void onGetOpenCloseSetting(EchoObject eoj, short tid, byte pdc, byte[] edt) {}
		/**
		 * Used to specify the Degree-of-opening level by selecting a level from among the 8 predefined levels, and to acquire the current setting.<br>0x31 to 0x38
		 */
		protected void onSetDegreeOfOpeniNgLevel(EchoObject eoj, short tid, boolean success) {}
		/**
		 * Used to specify the Degree-of-opening level by selecting a level from among the 8 predefined levels, and to acquire the current setting.<br>0x31 to 0x38
		 */
		protected void onGetDegreeOfOpeniNgLevel(EchoObject eoj, short tid, byte pdc, byte[] edt) {}
		/**
		 * Shade angle value<br>0x00.0xB4 (0.180. )
		 */
		protected void onSetSetValueOfShadeAngle(EchoObject eoj, short tid, boolean success) {}
		/**
		 * Shade angle value<br>0x00.0xB4 (0.180. )
		 */
		protected void onGetSetValueOfShadeAngle(EchoObject eoj, short tid, byte pdc, byte[] edt) {}
		/**
		 * Low/Medium/High<br>Low = 0x41, Medium = 0x42, High = 0x43
		 */
		protected void onSetShadeOpenCloseSpeed(EchoObject eoj, short tid, boolean success) {}
		/**
		 * Low/Medium/High<br>Low = 0x41, Medium = 0x42, High = 0x43
		 */
		protected void onGetShadeOpenCloseSpeed(EchoObject eoj, short tid, byte pdc, byte[] edt) {}

	}
	
	public interface Setter extends DeviceObject.Setter {
		public Setter reqSetPower(byte[] edt);
		public Setter reqSetInstallationLocation(byte[] edt);
		public Setter reqSetCurrentLimiting(byte[] edt);
		public Setter reqSetPowerSaving(byte[] edt);
		public Setter reqSetLocation(byte[] edt);
		public Setter reqSetCurrentTime(byte[] edt);
		public Setter reqSetCurrentDate(byte[] edt);
		public Setter reqSetPowerLimitation(byte[] edt);
		
		/**
		 * Open/close<br>Open = 0x41, close = 0x42
		 */
		public Setter reqSetOpenCloseSetting(byte[] edt);
		/**
		 * Used to specify the Degree-of-opening level by selecting a level from among the 8 predefined levels, and to acquire the current setting.<br>0x31 to 0x38
		 */
		public Setter reqSetDegreeOfOpeniNgLevel(byte[] edt);
		/**
		 * Shade angle value<br>0x00.0xB4 (0.180. )
		 */
		public Setter reqSetSetValueOfShadeAngle(byte[] edt);
		/**
		 * Low/Medium/High<br>Low = 0x41, Medium = 0x42, High = 0x43
		 */
		public Setter reqSetShadeOpenCloseSpeed(byte[] edt);

	}

	public class SetterImpl extends DeviceObject.SetterImpl implements Setter {

		public SetterImpl(byte esv) {
			super(esv);
		}
		
		@Override
		public Setter reqSetPower(byte[] edt) {
			return (Setter)super.reqSetPower(edt);
		}

		@Override
		public Setter reqSetInstallationLocation(byte[] edt) {
			return (Setter)super.reqSetInstallationLocation(edt);
		}

		@Override
		public Setter reqSetCurrentLimiting(byte[] edt) {
			return (Setter)super.reqSetCurrentLimiting(edt);
		}

		@Override
		public Setter reqSetPowerSaving(byte[] edt) {
			return (Setter)super.reqSetPowerSaving(edt);
		}

		@Override
		public Setter reqSetLocation(byte[] edt) {
			return (Setter)super.reqSetLocation(edt);
		}

		@Override
		public Setter reqSetCurrentTime(byte[] edt) {
			return (Setter)super.reqSetCurrentTime(edt);
		}

		@Override
		public Setter reqSetCurrentDate(byte[] edt) {
			return (Setter)super.reqSetCurrentDate(edt);
		}

		@Override
		public Setter reqSetPowerLimitation(byte[] edt) {
			return (Setter)super.reqSetPowerLimitation(edt);
		}

		@Override
		public Setter reqSetOpenCloseSetting(byte[] edt) {
			addProperty(EPC_OPEN_CLOSE_SETTING, edt, setOpenCloseSetting(edt));
			return this;
		}
		@Override
		public Setter reqSetDegreeOfOpeniNgLevel(byte[] edt) {
			addProperty(EPC_DEGREE_OF_OPENI_NG_LEVEL, edt, setDegreeOfOpeniNgLevel(edt));
			return this;
		}
		@Override
		public Setter reqSetSetValueOfShadeAngle(byte[] edt) {
			addProperty(EPC_SET_VALUE_OF_SHADE_ANGLE, edt, setSetValueOfShadeAngle(edt));
			return this;
		}
		@Override
		public Setter reqSetShadeOpenCloseSpeed(byte[] edt) {
			addProperty(EPC_SHADE_OPEN_CLOSE_SPEED, edt, setShadeOpenCloseSpeed(edt));
			return this;
		}

	}
	
	public class SetterProxy extends DeviceObject.SetterProxy implements Setter {

		public SetterProxy(byte esv) {
			super(esv);
		}

		
		@Override
		public Setter reqSetPower(byte[] edt) {
			return (Setter)super.reqSetPower(edt);
		}

		@Override
		public Setter reqSetInstallationLocation(byte[] edt) {
			return (Setter)super.reqSetInstallationLocation(edt);
		}

		@Override
		public Setter reqSetCurrentLimiting(byte[] edt) {
			return (Setter)super.reqSetCurrentLimiting(edt);
		}

		@Override
		public Setter reqSetPowerSaving(byte[] edt) {
			return (Setter)super.reqSetPowerSaving(edt);
		}

		@Override
		public Setter reqSetLocation(byte[] edt) {
			return (Setter)super.reqSetLocation(edt);
		}

		@Override
		public Setter reqSetCurrentTime(byte[] edt) {
			return (Setter)super.reqSetCurrentTime(edt);
		}

		@Override
		public Setter reqSetCurrentDate(byte[] edt) {
			return (Setter)super.reqSetCurrentDate(edt);
		}

		@Override
		public Setter reqSetPowerLimitation(byte[] edt) {
			return (Setter)super.reqSetPowerLimitation(edt);
		}

		@Override
		public Setter reqSetOpenCloseSetting(byte[] edt) {
			addProperty(EPC_OPEN_CLOSE_SETTING, edt, (edt != null && (edt.length == 1)));
			return this;
		}
		@Override
		public Setter reqSetDegreeOfOpeniNgLevel(byte[] edt) {
			addProperty(EPC_DEGREE_OF_OPENI_NG_LEVEL, edt, (edt != null && (edt.length == 1)));
			return this;
		}
		@Override
		public Setter reqSetSetValueOfShadeAngle(byte[] edt) {
			addProperty(EPC_SET_VALUE_OF_SHADE_ANGLE, edt, (edt != null && (edt.length == 1)));
			return this;
		}
		@Override
		public Setter reqSetShadeOpenCloseSpeed(byte[] edt) {
			addProperty(EPC_SHADE_OPEN_CLOSE_SPEED, edt, (edt != null && (edt.length == 1)));
			return this;
		}

	}

	public interface Getter extends DeviceObject.Getter {
		public Getter reqGetPower();
		public Getter reqGetInstallationLocation();
		public Getter reqGetVersion();
		public Getter reqGetIdNumber();
		public Getter reqGetElectricityConsumption();
		public Getter reqGetPowerConsumption();
		public Getter reqGetMakerErrorCode();
		public Getter reqGetCurrentLimiting();
		public Getter reqGetError();
		public Getter reqGetErrorInfo();
		public Getter reqGetMakerCode();
		public Getter reqGetWorkplaceCode();
		public Getter reqGetProductCode();
		public Getter reqGetManufacturingNumber();
		public Getter reqGetDateOfManufacture();
		public Getter reqGetPowerSaving();
		public Getter reqGetLocation();
		public Getter reqGetCurrentTime();
		public Getter reqGetCurrentDate();
		public Getter reqGetPowerLimitation();
		public Getter reqGetWorkingTime();
		public Getter reqGetAnnoPropertyMap();
		public Getter reqGetSetPropertyMap();
		public Getter reqGetGetPropertyMap();
		
		/**
		 * Open/close<br>Open = 0x41, close = 0x42
		 */
		public Getter reqGetOpenCloseSetting();
		/**
		 * Used to specify the Degree-of-opening level by selecting a level from among the 8 predefined levels, and to acquire the current setting.<br>0x31 to 0x38
		 */
		public Getter reqGetDegreeOfOpeniNgLevel();
		/**
		 * Shade angle value<br>0x00.0xB4 (0.180. )
		 */
		public Getter reqGetSetValueOfShadeAngle();
		/**
		 * Low/Medium/High<br>Low = 0x41, Medium = 0x42, High = 0x43
		 */
		public Getter reqGetShadeOpenCloseSpeed();

	}
	
	public class GetterImpl extends DeviceObject.GetterImpl implements Getter {

		@Override
		public Getter reqGetPower() {
			return (Getter)super.reqGetPower();
		}

		@Override
		public Getter reqGetInstallationLocation() {
			return (Getter)super.reqGetInstallationLocation();
		}

		@Override
		public Getter reqGetVersion() {
			return (Getter)super.reqGetVersion();
		}

		@Override
		public Getter reqGetIdNumber() {
			return (Getter)super.reqGetIdNumber();
		}

		@Override
		public Getter reqGetElectricityConsumption() {
			return (Getter)super.reqGetElectricityConsumption();
		}

		@Override
		public Getter reqGetPowerConsumption() {
			return (Getter)super.reqGetPowerConsumption();
		}

		@Override
		public Getter reqGetMakerErrorCode() {
			return (Getter)super.reqGetMakerErrorCode();
		}

		@Override
		public Getter reqGetCurrentLimiting() {
			return (Getter)super.reqGetCurrentLimiting();
		}

		@Override
		public Getter reqGetError() {
			return (Getter)super.reqGetError();
		}

		@Override
		public Getter reqGetErrorInfo() {
			return (Getter)super.reqGetErrorInfo();
		}

		@Override
		public Getter reqGetMakerCode() {
			return (Getter)super.reqGetMakerCode();
		}

		@Override
		public Getter reqGetWorkplaceCode() {
			return (Getter)super.reqGetWorkplaceCode();
		}

		@Override
		public Getter reqGetProductCode() {
			return (Getter)super.reqGetProductCode();
		}

		@Override
		public Getter reqGetManufacturingNumber() {
			return (Getter)super.reqGetManufacturingNumber();
		}

		@Override
		public Getter reqGetDateOfManufacture() {
			return (Getter)super.reqGetDateOfManufacture();
		}

		@Override
		public Getter reqGetPowerSaving() {
			return (Getter)super.reqGetPowerSaving();
		}

		@Override
		public Getter reqGetLocation() {
			return (Getter)super.reqGetLocation();
		}

		@Override
		public Getter reqGetCurrentTime() {
			return (Getter)super.reqGetCurrentTime();
		}

		@Override
		public Getter reqGetCurrentDate() {
			return (Getter)super.reqGetCurrentDate();
		}

		@Override
		public Getter reqGetPowerLimitation() {
			return (Getter)super.reqGetPowerLimitation();
		}

		@Override
		public Getter reqGetWorkingTime() {
			return (Getter)super.reqGetWorkingTime();
		}

		@Override
		public Getter reqGetAnnoPropertyMap() {
			return (Getter)super.reqGetAnnoPropertyMap();
		}

		@Override
		public Getter reqGetSetPropertyMap() {
			return (Getter)super.reqGetSetPropertyMap();
		}

		@Override
		public Getter reqGetGetPropertyMap() {
			return (Getter)super.reqGetGetPropertyMap();
		}

		@Override
		public Getter reqGetOpenCloseSetting() {
			byte[] edt = getOpenCloseSetting();
			addProperty(EPC_OPEN_CLOSE_SETTING, edt, (edt != null && (edt.length == 1)));
			return this;
		}
		@Override
		public Getter reqGetDegreeOfOpeniNgLevel() {
			byte[] edt = getDegreeOfOpeniNgLevel();
			addProperty(EPC_DEGREE_OF_OPENI_NG_LEVEL, edt, (edt != null && (edt.length == 1)));
			return this;
		}
		@Override
		public Getter reqGetSetValueOfShadeAngle() {
			byte[] edt = getSetValueOfShadeAngle();
			addProperty(EPC_SET_VALUE_OF_SHADE_ANGLE, edt, (edt != null && (edt.length == 1)));
			return this;
		}
		@Override
		public Getter reqGetShadeOpenCloseSpeed() {
			byte[] edt = getShadeOpenCloseSpeed();
			addProperty(EPC_SHADE_OPEN_CLOSE_SPEED, edt, (edt != null && (edt.length == 1)));
			return this;
		}

	}

	public class GetterProxy extends DeviceObject.GetterProxy implements Getter {

		@Override
		public Getter reqGetPower() {
			return (Getter)super.reqGetPower();
		}

		@Override
		public Getter reqGetInstallationLocation() {
			return (Getter)super.reqGetInstallationLocation();
		}

		@Override
		public Getter reqGetVersion() {
			return (Getter)super.reqGetVersion();
		}

		@Override
		public Getter reqGetIdNumber() {
			return (Getter)super.reqGetIdNumber();
		}

		@Override
		public Getter reqGetElectricityConsumption() {
			return (Getter)super.reqGetElectricityConsumption();
		}

		@Override
		public Getter reqGetPowerConsumption() {
			return (Getter)super.reqGetPowerConsumption();
		}

		@Override
		public Getter reqGetMakerErrorCode() {
			return (Getter)super.reqGetMakerErrorCode();
		}

		@Override
		public Getter reqGetCurrentLimiting() {
			return (Getter)super.reqGetCurrentLimiting();
		}

		@Override
		public Getter reqGetError() {
			return (Getter)super.reqGetError();
		}

		@Override
		public Getter reqGetErrorInfo() {
			return (Getter)super.reqGetErrorInfo();
		}

		@Override
		public Getter reqGetMakerCode() {
			return (Getter)super.reqGetMakerCode();
		}

		@Override
		public Getter reqGetWorkplaceCode() {
			return (Getter)super.reqGetWorkplaceCode();
		}

		@Override
		public Getter reqGetProductCode() {
			return (Getter)super.reqGetProductCode();
		}

		@Override
		public Getter reqGetManufacturingNumber() {
			return (Getter)super.reqGetManufacturingNumber();
		}

		@Override
		public Getter reqGetDateOfManufacture() {
			return (Getter)super.reqGetDateOfManufacture();
		}

		@Override
		public Getter reqGetPowerSaving() {
			return (Getter)super.reqGetPowerSaving();
		}

		@Override
		public Getter reqGetLocation() {
			return (Getter)super.reqGetLocation();
		}

		@Override
		public Getter reqGetCurrentTime() {
			return (Getter)super.reqGetCurrentTime();
		}

		@Override
		public Getter reqGetCurrentDate() {
			return (Getter)super.reqGetCurrentDate();
		}

		@Override
		public Getter reqGetPowerLimitation() {
			return (Getter)super.reqGetPowerLimitation();
		}

		@Override
		public Getter reqGetWorkingTime() {
			return (Getter)super.reqGetWorkingTime();
		}

		@Override
		public Getter reqGetAnnoPropertyMap() {
			return (Getter)super.reqGetAnnoPropertyMap();
		}

		@Override
		public Getter reqGetSetPropertyMap() {
			return (Getter)super.reqGetSetPropertyMap();
		}

		@Override
		public Getter reqGetGetPropertyMap() {
			return (Getter)super.reqGetGetPropertyMap();
		}

		@Override
		public Getter reqGetOpenCloseSetting() {
			addProperty(EPC_OPEN_CLOSE_SETTING);
			return this;
		}
		@Override
		public Getter reqGetDegreeOfOpeniNgLevel() {
			addProperty(EPC_DEGREE_OF_OPENI_NG_LEVEL);
			return this;
		}
		@Override
		public Getter reqGetSetValueOfShadeAngle() {
			addProperty(EPC_SET_VALUE_OF_SHADE_ANGLE);
			return this;
		}
		@Override
		public Getter reqGetShadeOpenCloseSpeed() {
			addProperty(EPC_SHADE_OPEN_CLOSE_SPEED);
			return this;
		}

	}
	
	public interface Informer extends DeviceObject.Informer {
		public Informer reqInformPower();
		public Informer reqInformInstallationLocation();
		public Informer reqInformVersion();
		public Informer reqInformIdNumber();
		public Informer reqInformElectricityConsumption();
		public Informer reqInformPowerConsumption();
		public Informer reqInformMakerErrorCode();
		public Informer reqInformCurrentLimiting();
		public Informer reqInformError();
		public Informer reqInformErrorInfo();
		public Informer reqInformMakerCode();
		public Informer reqInformWorkplaceCode();
		public Informer reqInformProductCode();
		public Informer reqInformManufacturingNumber();
		public Informer reqInformDateOfManufacture();
		public Informer reqInformPowerSaving();
		public Informer reqInformLocation();
		public Informer reqInformCurrentTime();
		public Informer reqInformCurrentDate();
		public Informer reqInformPowerLimitation();
		public Informer reqInformWorkingTime();
		public Informer reqInformAnnoPropertyMap();
		public Informer reqInformSetPropertyMap();
		public Informer reqInformGetPropertyMap();
		
		/**
		 * Open/close<br>Open = 0x41, close = 0x42
		 */
		public Informer reqInformOpenCloseSetting();
		/**
		 * Used to specify the Degree-of-opening level by selecting a level from among the 8 predefined levels, and to acquire the current setting.<br>0x31 to 0x38
		 */
		public Informer reqInformDegreeOfOpeniNgLevel();
		/**
		 * Shade angle value<br>0x00.0xB4 (0.180. )
		 */
		public Informer reqInformSetValueOfShadeAngle();
		/**
		 * Low/Medium/High<br>Low = 0x41, Medium = 0x42, High = 0x43
		 */
		public Informer reqInformShadeOpenCloseSpeed();

	}

	public class InformerImpl extends DeviceObject.InformerImpl implements Informer {

		@Override
		public Informer reqInformPower() {
			return (Informer)super.reqInformPower();
		}

		@Override
		public Informer reqInformInstallationLocation() {
			return (Informer)super.reqInformInstallationLocation();
		}

		@Override
		public Informer reqInformVersion() {
			return (Informer)super.reqInformVersion();
		}

		@Override
		public Informer reqInformIdNumber() {
			return (Informer)super.reqInformIdNumber();
		}

		@Override
		public Informer reqInformElectricityConsumption() {
			return (Informer)super.reqInformElectricityConsumption();
		}

		@Override
		public Informer reqInformPowerConsumption() {
			return (Informer)super.reqInformPowerConsumption();
		}

		@Override
		public Informer reqInformMakerErrorCode() {
			return (Informer)super.reqInformMakerErrorCode();
		}

		@Override
		public Informer reqInformCurrentLimiting() {
			return (Informer)super.reqInformCurrentLimiting();
		}

		@Override
		public Informer reqInformError() {
			return (Informer)super.reqInformError();
		}

		@Override
		public Informer reqInformErrorInfo() {
			return (Informer)super.reqInformErrorInfo();
		}

		@Override
		public Informer reqInformMakerCode() {
			return (Informer)super.reqInformMakerCode();
		}

		@Override
		public Informer reqInformWorkplaceCode() {
			return (Informer)super.reqInformWorkplaceCode();
		}

		@Override
		public Informer reqInformProductCode() {
			return (Informer)super.reqInformProductCode();
		}

		@Override
		public Informer reqInformManufacturingNumber() {
			return (Informer)super.reqInformManufacturingNumber();
		}

		@Override
		public Informer reqInformDateOfManufacture() {
			return (Informer)super.reqInformDateOfManufacture();
		}

		@Override
		public Informer reqInformPowerSaving() {
			return (Informer)super.reqInformPowerSaving();
		}

		@Override
		public Informer reqInformLocation() {
			return (Informer)super.reqInformLocation();
		}

		@Override
		public Informer reqInformCurrentTime() {
			return (Informer)super.reqInformCurrentTime();
		}

		@Override
		public Informer reqInformCurrentDate() {
			return (Informer)super.reqInformCurrentDate();
		}

		@Override
		public Informer reqInformPowerLimitation() {
			return (Informer)super.reqInformPowerLimitation();
		}

		@Override
		public Informer reqInformWorkingTime() {
			return (Informer)super.reqInformWorkingTime();
		}

		@Override
		public Informer reqInformAnnoPropertyMap() {
			return (Informer)super.reqInformAnnoPropertyMap();
		}

		@Override
		public Informer reqInformSetPropertyMap() {
			return (Informer)super.reqInformSetPropertyMap();
		}

		@Override
		public Informer reqInformGetPropertyMap() {
			return (Informer)super.reqInformGetPropertyMap();
		}

		@Override
		public Informer reqInformOpenCloseSetting() {
			byte[] edt = getOpenCloseSetting();
			addProperty(EPC_OPEN_CLOSE_SETTING, edt, (edt != null && (edt.length == 1)));
			return this;
		}
		@Override
		public Informer reqInformDegreeOfOpeniNgLevel() {
			byte[] edt = getDegreeOfOpeniNgLevel();
			addProperty(EPC_DEGREE_OF_OPENI_NG_LEVEL, edt, (edt != null && (edt.length == 1)));
			return this;
		}
		@Override
		public Informer reqInformSetValueOfShadeAngle() {
			byte[] edt = getSetValueOfShadeAngle();
			addProperty(EPC_SET_VALUE_OF_SHADE_ANGLE, edt, (edt != null && (edt.length == 1)));
			return this;
		}
		@Override
		public Informer reqInformShadeOpenCloseSpeed() {
			byte[] edt = getShadeOpenCloseSpeed();
			addProperty(EPC_SHADE_OPEN_CLOSE_SPEED, edt, (edt != null && (edt.length == 1)));
			return this;
		}

	}
	
	public class InformerProxy extends DeviceObject.InformerProxy implements Informer {

		@Override
		public Informer reqInformPower() {
			return (Informer)super.reqInformPower();
		}

		@Override
		public Informer reqInformInstallationLocation() {
			return (Informer)super.reqInformInstallationLocation();
		}

		@Override
		public Informer reqInformVersion() {
			return (Informer)super.reqInformVersion();
		}

		@Override
		public Informer reqInformIdNumber() {
			return (Informer)super.reqInformIdNumber();
		}

		@Override
		public Informer reqInformElectricityConsumption() {
			return (Informer)super.reqInformElectricityConsumption();
		}

		@Override
		public Informer reqInformPowerConsumption() {
			return (Informer)super.reqInformPowerConsumption();
		}

		@Override
		public Informer reqInformMakerErrorCode() {
			return (Informer)super.reqInformMakerErrorCode();
		}

		@Override
		public Informer reqInformCurrentLimiting() {
			return (Informer)super.reqInformCurrentLimiting();
		}

		@Override
		public Informer reqInformError() {
			return (Informer)super.reqInformError();
		}

		@Override
		public Informer reqInformErrorInfo() {
			return (Informer)super.reqInformErrorInfo();
		}

		@Override
		public Informer reqInformMakerCode() {
			return (Informer)super.reqInformMakerCode();
		}

		@Override
		public Informer reqInformWorkplaceCode() {
			return (Informer)super.reqInformWorkplaceCode();
		}

		@Override
		public Informer reqInformProductCode() {
			return (Informer)super.reqInformProductCode();
		}

		@Override
		public Informer reqInformManufacturingNumber() {
			return (Informer)super.reqInformManufacturingNumber();
		}

		@Override
		public Informer reqInformDateOfManufacture() {
			return (Informer)super.reqInformDateOfManufacture();
		}

		@Override
		public Informer reqInformPowerSaving() {
			return (Informer)super.reqInformPowerSaving();
		}

		@Override
		public Informer reqInformLocation() {
			return (Informer)super.reqInformLocation();
		}

		@Override
		public Informer reqInformCurrentTime() {
			return (Informer)super.reqInformCurrentTime();
		}

		@Override
		public Informer reqInformCurrentDate() {
			return (Informer)super.reqInformCurrentDate();
		}

		@Override
		public Informer reqInformPowerLimitation() {
			return (Informer)super.reqInformPowerLimitation();
		}

		@Override
		public Informer reqInformWorkingTime() {
			return (Informer)super.reqInformWorkingTime();
		}

		@Override
		public Informer reqInformAnnoPropertyMap() {
			return (Informer)super.reqInformAnnoPropertyMap();
		}

		@Override
		public Informer reqInformSetPropertyMap() {
			return (Informer)super.reqInformSetPropertyMap();
		}

		@Override
		public Informer reqInformGetPropertyMap() {
			return (Informer)super.reqInformGetPropertyMap();
		}

		@Override
		public Informer reqInformOpenCloseSetting() {
			addProperty(EPC_OPEN_CLOSE_SETTING);
			return this;
		}
		@Override
		public Informer reqInformDegreeOfOpeniNgLevel() {
			addProperty(EPC_DEGREE_OF_OPENI_NG_LEVEL);
			return this;
		}
		@Override
		public Informer reqInformSetValueOfShadeAngle() {
			addProperty(EPC_SET_VALUE_OF_SHADE_ANGLE);
			return this;
		}
		@Override
		public Informer reqInformShadeOpenCloseSpeed() {
			addProperty(EPC_SHADE_OPEN_CLOSE_SPEED);
			return this;
		}

	}
}