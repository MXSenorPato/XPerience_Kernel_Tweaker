/*
 * Copyright (C) 2014 Carlos Jesús <TeamMEX@XDA-Developers>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package com.klozz.performance.utils;

import android.os.Environment;

import com.klozz.performance.fragments.BackupFragment;
import com.klozz.performance.fragments.BuildpropFragment;
import com.klozz.performance.fragments.CPUFragment;
import com.klozz.performance.fragments.CpuStatsFragment;
import com.klozz.performance.fragments.CustomCommanderFragment;
import com.klozz.performance.fragments.GPUFragment;
import com.klozz.performance.fragments.IOSchedulerFragment;
import com.klozz.performance.fragments.KernelInformationFragment;
import com.klozz.performance.fragments.LowMemoryKillerFragment;
import com.klozz.performance.fragments.MemoryStatsFragment;
import com.klozz.performance.fragments.TimeInStateFragment;
import com.klozz.performance.fragments.VirtualMachineFragment;
import com.klozz.performance.helpers.CPUHelper;
import com.klozz.performance.helpers.GPUHelper;
import com.klozz.performance.helpers.IOHelper;
import com.klozz.performance.helpers.LowMemoryKillerHelper;
import com.klozz.performance.helpers.PreferenceHelper;
import com.klozz.performance.helpers.RootHelper;
import com.klozz.performance.helpers.VirtualMachineHelper;

public interface Constants {

    public final String TAG = "XPerience Performance";
    public final String PREF_NAME = "commands";
    public final String COMMAND_NAME = "names";

    public final String PROC_VERSION = "/proc/version";
    public final String PROC_CPUINFO = "/proc/cpuinfo";
    public final String PROC_MEMINFO = "/proc/meminfo";
    public final String PROC_STAT = "/proc/stat";

    public final String EXTERNAL_STORAGE_DIRECTORY = Environment
            .getExternalStorageDirectory().toString();
    public final String XPe_BACKUP = EXTERNAL_STORAGE_DIRECTORY + "/XPeBackup";

    public final int enter_anim = android.R.anim.fade_in;
    public final int exit_anim = android.R.anim.fade_out;

    public final BackupFragment mBackupFragment = new BackupFragment();
    public final BuildpropFragment mBuildpropFragment = new BuildpropFragment();
    public final CPUFragment mCPUFragment = new CPUFragment();
    public final CpuStatsFragment mCpuStatsFragment = new CpuStatsFragment();
    public final CustomCommanderFragment mCustomCommanderFragment = new CustomCommanderFragment();
    public final GPUFragment mGPUFragment = new GPUFragment();
    public final KernelInformationFragment mKernelInformationFragment = new KernelInformationFragment();
    public final IOSchedulerFragment mIOSchedulerFragment = new IOSchedulerFragment();
    public final LowMemoryKillerFragment mLowMemoryKillerFragment = new LowMemoryKillerFragment();
    public final MemoryStatsFragment mMemoryStatsFragment = new MemoryStatsFragment();
    public final TimeInStateFragment mTimeInStateFragment = new TimeInStateFragment();
    public final VirtualMachineFragment mVirtualMachineFragment = new VirtualMachineFragment();

    public final CommandControl mCommandControl = new CommandControl();
    public final Dialog mDialog = new Dialog();
    public final Utils mUtils = new Utils();

    public final CPUHelper cpuHelper = new CPUHelper();
    public final GPUHelper gpuHelper = new GPUHelper();
    public final IOHelper ioHelper = new IOHelper();
    public final LowMemoryKillerHelper lowmemorykillerHelper = new LowMemoryKillerHelper();
    public final PreferenceHelper prefHelper = new PreferenceHelper();
    public final RootHelper rootHelper = new RootHelper();
    public final VirtualMachineHelper virtualmachineHelper = new VirtualMachineHelper();

    public final String PARTITON_PATH = "/dev/block/platform";
    public final String BUILD_PROP = "/system/build.prop";

    public final String CPU_CUR_FREQ = "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_cur_freq";
    public final String CPU_CORE_ONLINE = "/sys/devices/system/cpu/cpu%d/online";
    public final String CPU_MAX_FREQ = "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_max_freq";
    public final String CPU_MIN_FREQ = "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_min_freq";
    public final String CPU_TIME_STATE = "/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state";
    public final String CPU_SCALING_GOVERNOR = "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_governor";
    public final String CPU_AVAILABLE_GOVERNORS = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors";
    public final String CPU_GOVERNOR_TUNABLES = "/sys/devices/system/cpu/cpufreq/%s";
    public final String CPU_MC_POWER_SAVING = "/sys/devices/system/cpu/sched_mc_power_savings";
    public final String CPU_MPDECISION_BINARY = "/system/bin/mpdecision";
    public final String CPU_MPDEC = "mpdecision";

    public final String GPU_GENERIC_GOVERNORS = "performance powersave ondemand simple";

    public final String GPU_CUR_KGSL2D0_QCOM_FREQ = "/sys/devices/platform/kgsl-2d0.0/kgsl/kgsl-2d0/gpuclk";
    public final String GPU_MAX_KGSL2D0_QCOM_FREQ = "/sys/devices/platform/kgsl-2d0.0/kgsl/kgsl-2d0/max_gpuclk";
    public final String GPU_AVAILABLE_KGSL2D0_QCOM_FREQS = "/sys/devices/platform/kgsl-2d0.0/kgsl/kgsl-2d0/gpu_available_frequencies";
    public final String GPU_SCALING_KGSL2D0_QCOM_GOVERNOR = "/sys/devices/platform/kgsl-2d0.0/kgsl/kgsl-2d0/pwrscale/trustzone/governor";

    public final String GPU_CUR_KGSL3D0_QCOM_FREQ = "/sys/devices/platform/kgsl-3d0.0/kgsl/kgsl-3d0/gpuclk";
    public final String GPU_MAX_KGSL3D0_QCOM_FREQ = "/sys/devices/platform/kgsl-3d0.0/kgsl/kgsl-3d0/max_gpuclk";
    public final String GPU_AVAILABLE_KGSL3D0_QCOM_FREQS = "/sys/devices/platform/kgsl-3d0.0/kgsl/kgsl-3d0/gpu_available_frequencies";
    public final String GPU_SCALING_KGSL3D0_QCOM_GOVERNOR = "/sys/devices/platform/kgsl-3d0.0/kgsl/kgsl-3d0/pwrscale/trustzone/governor";

    public final String GPU_CUR_FDB00000_QCOM_FREQ = "/sys/devices/fdb00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/devfreq/cur_freq";
    public final String GPU_MAX_FDB00000_QCOM_FREQ = "/sys/devices/fdb00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/max_gpuclk";
    public final String GPU_AVAILABLE_FDB00000_QCOM_FREQS = "/sys/devices/fdb00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/gpu_available_frequencies";
    public final String GPU_SCALING_FDB00000_QCOM_GOVERNOR = "/sys/devices/fdb00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/devfreq/governor";
    public final String GPU_AVAILABLE_FDB00000_QCOM_GOVERNORS = "/sys/devices/fdb00000.qcom,kgsl-3d0/kgsl/kgsl-3d0/devfreq/available_governors";

    public final String[] GPU_2D_CUR_FREQ_ARRAY = new String[] { GPU_CUR_KGSL2D0_QCOM_FREQ };

    public final String[] GPU_2D_MAX_FREQ_ARRAY = new String[] { GPU_MAX_KGSL2D0_QCOM_FREQ };

    public final String[] GPU_2D_AVAILABLE_FREQS_ARRAY = new String[] { GPU_AVAILABLE_KGSL2D0_QCOM_FREQS };

    public final String[] GPU_2D_SCALING_GOVERNOR_ARRAY = new String[] { GPU_SCALING_KGSL2D0_QCOM_GOVERNOR };

    public final String[] GPU_3D_CUR_FREQ_ARRAY = new String[] {
            GPU_CUR_KGSL3D0_QCOM_FREQ, GPU_CUR_FDB00000_QCOM_FREQ };

    public final String[] GPU_3D_MAX_FREQ_ARRAY = new String[] {
            GPU_MAX_KGSL3D0_QCOM_FREQ, GPU_MAX_FDB00000_QCOM_FREQ };

    public final String[] GPU_3D_AVAILABLE_FREQS_ARRAY = new String[] {
            GPU_AVAILABLE_KGSL3D0_QCOM_FREQS, GPU_AVAILABLE_FDB00000_QCOM_FREQS };

    public final String[] GPU_3D_SCALING_GOVERNOR_ARRAY = new String[] {
            GPU_SCALING_KGSL3D0_QCOM_GOVERNOR,
            GPU_SCALING_FDB00000_QCOM_GOVERNOR };

    public final String[] GPU_3D_AVAILABLE_GOVERNORS_ARRAY = new String[] { GPU_AVAILABLE_FDB00000_QCOM_GOVERNORS };

    public final String[][] GPU_ARRAY = new String[][] { GPU_2D_CUR_FREQ_ARRAY,
            GPU_2D_MAX_FREQ_ARRAY, GPU_2D_AVAILABLE_FREQS_ARRAY,
            GPU_2D_SCALING_GOVERNOR_ARRAY, GPU_3D_CUR_FREQ_ARRAY,
            GPU_3D_MAX_FREQ_ARRAY, GPU_3D_AVAILABLE_FREQS_ARRAY,
            GPU_3D_SCALING_GOVERNOR_ARRAY };

    public final String IO_INTERNAL_SCHEDULER = "/sys/block/mmcblk0/queue/scheduler";
    public final String IO_EXTERNAL_SCHEDULER = "/sys/block/mmcblk1/queue/scheduler";
    public final String IO_INTERNAL_SCHEDULER_TUNABLE = "/sys/block/mmcblk0/queue/iosched";
    public final String IO_EXTERNAL_SCHEDULER_TUNABLE = "/sys/block/mmcblk1/queue/iosched";
    public final String IO_INTERNAL_READ_AHEAD = "/sys/block/mmcblk0/queue/read_ahead_kb";
    public final String IO_EXTERNAL_READ_AHEAD = "/sys/block/mmcblk1/queue/read_ahead_kb";

    public final String LMK_MINFREE = "/sys/module/lowmemorykiller/parameters/minfree";

    public final String VM_PATH = "/proc/sys/vm";
}
