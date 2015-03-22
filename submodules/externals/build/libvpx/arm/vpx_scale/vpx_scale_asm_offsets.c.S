	.arch armv5te
	.fpu softvfp
	.eabi_attribute 20, 1
	.eabi_attribute 21, 1
	.eabi_attribute 23, 3
	.eabi_attribute 24, 1
	.eabi_attribute 25, 1
	.eabi_attribute 26, 2
	.eabi_attribute 30, 2
	.eabi_attribute 18, 4
	.file	"vpx_scale_asm_offsets.c"
	.text
.Ltext0:
	.cfi_sections	.debug_frame
	.section	.text.startup,"ax",%progbits
	.align	2
	.global	main
	.type	main, %function
main:
.LFB0:
	.file 1 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vpx_scale/vpx_scale_asm_offsets.c"
	.loc 1 17 0
	.cfi_startproc
	@ args = 0, pretend = 0, frame = 0
	@ frame_needed = 0, uses_anonymous_args = 0
	@ link register save eliminated.
	.loc 1 20 0
#APP
@ 20 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vpx_scale/vpx_scale_asm_offsets.c" 1
	
yv12_buffer_config_y_width EQU #0
@ 0 "" 2
	.loc 1 21 0
@ 21 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vpx_scale/vpx_scale_asm_offsets.c" 1
	
yv12_buffer_config_y_height EQU #4
@ 0 "" 2
	.loc 1 22 0
@ 22 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vpx_scale/vpx_scale_asm_offsets.c" 1
	
yv12_buffer_config_y_stride EQU #16
@ 0 "" 2
	.loc 1 23 0
@ 23 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vpx_scale/vpx_scale_asm_offsets.c" 1
	
yv12_buffer_config_uv_width EQU #20
@ 0 "" 2
	.loc 1 24 0
@ 24 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vpx_scale/vpx_scale_asm_offsets.c" 1
	
yv12_buffer_config_uv_height EQU #24
@ 0 "" 2
	.loc 1 25 0
@ 25 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vpx_scale/vpx_scale_asm_offsets.c" 1
	
yv12_buffer_config_uv_stride EQU #36
@ 0 "" 2
	.loc 1 26 0
@ 26 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vpx_scale/vpx_scale_asm_offsets.c" 1
	
yv12_buffer_config_y_buffer EQU #52
@ 0 "" 2
	.loc 1 27 0
@ 27 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vpx_scale/vpx_scale_asm_offsets.c" 1
	
yv12_buffer_config_u_buffer EQU #56
@ 0 "" 2
	.loc 1 28 0
@ 28 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vpx_scale/vpx_scale_asm_offsets.c" 1
	
yv12_buffer_config_v_buffer EQU #60
@ 0 "" 2
	.loc 1 29 0
@ 29 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vpx_scale/vpx_scale_asm_offsets.c" 1
	
yv12_buffer_config_border EQU #76
@ 0 "" 2
	.loc 1 30 0
@ 30 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vpx_scale/vpx_scale_asm_offsets.c" 1
	
VP8BORDERINPIXELS_VAL EQU #32
@ 0 "" 2
	.loc 1 32 0
	mov	r0, #0
	bx	lr
	.cfi_endproc
.LFE0:
	.size	main, .-main
	.text
.Letext0:
	.section	.debug_info,"",%progbits
.Ldebug_info0:
	.4byte	0x93
	.2byte	0x2
	.4byte	.Ldebug_abbrev0
	.byte	0x4
	.uleb128 0x1
	.4byte	.LASF10
	.byte	0x1
	.4byte	.LASF11
	.4byte	.LASF12
	.4byte	0
	.4byte	0
	.4byte	.Ldebug_ranges0+0
	.4byte	.Ldebug_line0
	.4byte	.Ldebug_pubnames0
	.4byte	.Ldebug_pubtypes0
	.uleb128 0x2
	.byte	0x4
	.byte	0x5
	.ascii	"int\000"
	.uleb128 0x3
	.byte	0x4
	.byte	0x7
	.4byte	.LASF0
	.uleb128 0x3
	.byte	0x1
	.byte	0x6
	.4byte	.LASF1
	.uleb128 0x3
	.byte	0x1
	.byte	0x8
	.4byte	.LASF2
	.uleb128 0x3
	.byte	0x2
	.byte	0x5
	.4byte	.LASF3
	.uleb128 0x3
	.byte	0x2
	.byte	0x7
	.4byte	.LASF4
	.uleb128 0x3
	.byte	0x8
	.byte	0x5
	.4byte	.LASF5
	.uleb128 0x3
	.byte	0x8
	.byte	0x7
	.4byte	.LASF6
	.uleb128 0x3
	.byte	0x4
	.byte	0x7
	.4byte	.LASF7
	.uleb128 0x3
	.byte	0x4
	.byte	0x5
	.4byte	.LASF8
	.uleb128 0x3
	.byte	0x1
	.byte	0x8
	.4byte	.LASF9
	.uleb128 0x4
	.byte	0x1
	.4byte	.LASF13
	.byte	0x1
	.byte	0x11
	.byte	0x1
	.4byte	0x31
	.4byte	.LFB0
	.4byte	.LFE0
	.byte	0x2
	.byte	0x7d
	.sleb128 0
	.byte	0
	.section	.debug_abbrev,"",%progbits
.Ldebug_abbrev0:
	.uleb128 0x1
	.uleb128 0x11
	.byte	0x1
	.uleb128 0x25
	.uleb128 0xe
	.uleb128 0x13
	.uleb128 0xb
	.uleb128 0x3
	.uleb128 0xe
	.uleb128 0x1b
	.uleb128 0xe
	.uleb128 0x11
	.uleb128 0x1
	.uleb128 0x52
	.uleb128 0x1
	.uleb128 0x55
	.uleb128 0x6
	.uleb128 0x10
	.uleb128 0x6
	.uleb128 0x2134
	.uleb128 0x6
	.uleb128 0x2135
	.uleb128 0x6
	.byte	0
	.byte	0
	.uleb128 0x2
	.uleb128 0x24
	.byte	0
	.uleb128 0xb
	.uleb128 0xb
	.uleb128 0x3e
	.uleb128 0xb
	.uleb128 0x3
	.uleb128 0x8
	.byte	0
	.byte	0
	.uleb128 0x3
	.uleb128 0x24
	.byte	0
	.uleb128 0xb
	.uleb128 0xb
	.uleb128 0x3e
	.uleb128 0xb
	.uleb128 0x3
	.uleb128 0xe
	.byte	0
	.byte	0
	.uleb128 0x4
	.uleb128 0x2e
	.byte	0
	.uleb128 0x3f
	.uleb128 0xc
	.uleb128 0x3
	.uleb128 0xe
	.uleb128 0x3a
	.uleb128 0xb
	.uleb128 0x3b
	.uleb128 0xb
	.uleb128 0x27
	.uleb128 0xc
	.uleb128 0x49
	.uleb128 0x13
	.uleb128 0x11
	.uleb128 0x1
	.uleb128 0x12
	.uleb128 0x1
	.uleb128 0x40
	.uleb128 0xa
	.byte	0
	.byte	0
	.byte	0
	.section	.debug_pubnames,"",%progbits
.Ldebug_pubnames0:
	.4byte	0x17
	.2byte	0x2
	.4byte	.Ldebug_info0
	.4byte	0x97
	.4byte	0x7e
	.ascii	"main\000"
	.4byte	0
	.section	.debug_pubtypes,"",%progbits
.Ldebug_pubtypes0:
	.4byte	0xc7
	.2byte	0x2
	.4byte	.Ldebug_info0
	.4byte	0x97
	.4byte	0x31
	.ascii	"int\000"
	.4byte	0x38
	.ascii	"unsigned int\000"
	.4byte	0x3f
	.ascii	"signed char\000"
	.4byte	0x46
	.ascii	"unsigned char\000"
	.4byte	0x4d
	.ascii	"short int\000"
	.4byte	0x54
	.ascii	"short unsigned int\000"
	.4byte	0x5b
	.ascii	"long long int\000"
	.4byte	0x62
	.ascii	"long long unsigned int\000"
	.4byte	0x69
	.ascii	"long unsigned int\000"
	.4byte	0x70
	.ascii	"long int\000"
	.4byte	0x77
	.ascii	"char\000"
	.4byte	0
	.section	.debug_aranges,"",%progbits
	.4byte	0x1c
	.2byte	0x2
	.4byte	.Ldebug_info0
	.byte	0x4
	.byte	0
	.2byte	0
	.2byte	0
	.4byte	.LFB0
	.4byte	.LFE0-.LFB0
	.4byte	0
	.4byte	0
	.section	.debug_ranges,"",%progbits
.Ldebug_ranges0:
	.4byte	.LFB0
	.4byte	.LFE0
	.4byte	0
	.4byte	0
	.section	.debug_line,"",%progbits
.Ldebug_line0:
	.section	.debug_str,"MS",%progbits,1
.LASF0:
	.ascii	"unsigned int\000"
.LASF7:
	.ascii	"long unsigned int\000"
.LASF8:
	.ascii	"long int\000"
.LASF2:
	.ascii	"unsigned char\000"
.LASF1:
	.ascii	"signed char\000"
.LASF10:
	.ascii	"GNU C 4.6 20120106 (prerelease)\000"
.LASF3:
	.ascii	"short int\000"
.LASF6:
	.ascii	"long long unsigned int\000"
.LASF4:
	.ascii	"short unsigned int\000"
.LASF11:
	.ascii	"/Users/macbookair/linphone-android/submodules/exter"
	.ascii	"nals/libvpx/vpx_scale/vpx_scale_asm_offsets.c\000"
.LASF13:
	.ascii	"main\000"
.LASF12:
	.ascii	"/Users/macbookair/linphone-android/submodules/exter"
	.ascii	"nals/build/libvpx/arm\000"
.LASF9:
	.ascii	"char\000"
.LASF5:
	.ascii	"long long int\000"
	.ident	"GCC: (GNU) 4.6 20120106 (prerelease)"
	.section	.note.GNU-stack,"",%progbits
