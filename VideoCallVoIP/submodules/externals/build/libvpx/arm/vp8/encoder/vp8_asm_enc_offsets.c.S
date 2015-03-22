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
	.file	"vp8_asm_enc_offsets.c"
	.text
.Ltext0:
	.cfi_sections	.debug_frame
	.section	.text.startup,"ax",%progbits
	.align	2
	.global	main
	.type	main, %function
main:
.LFB99:
	.file 1 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c"
	.loc 1 20 0
	.cfi_startproc
	@ args = 0, pretend = 0, frame = 0
	@ frame_needed = 0, uses_anonymous_args = 0
	@ link register save eliminated.
	.loc 1 23 0
#APP
@ 23 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_block_coeff EQU #4
@ 0 "" 2
	.loc 1 24 0
@ 24 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_block_zbin EQU #20
@ 0 "" 2
	.loc 1 25 0
@ 25 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_block_round EQU #28
@ 0 "" 2
	.loc 1 26 0
@ 26 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_block_quant EQU #8
@ 0 "" 2
	.loc 1 27 0
@ 27 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_block_quant_fast EQU #12
@ 0 "" 2
	.loc 1 28 0
@ 28 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_block_zbin_extra EQU #32
@ 0 "" 2
	.loc 1 29 0
@ 29 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_block_zrun_zbin_boost EQU #24
@ 0 "" 2
	.loc 1 30 0
@ 30 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_block_quant_shift EQU #16
@ 0 "" 2
	.loc 1 32 0
@ 32 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_blockd_qcoeff EQU #0
@ 0 "" 2
	.loc 1 33 0
@ 33 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_blockd_dequant EQU #12
@ 0 "" 2
	.loc 1 34 0
@ 34 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_blockd_dqcoeff EQU #4
@ 0 "" 2
	.loc 1 35 0
@ 35 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_blockd_eob EQU #20
@ 0 "" 2
	.loc 1 38 0
@ 38 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_block_base_src EQU #36
@ 0 "" 2
	.loc 1 39 0
@ 39 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_block_src EQU #40
@ 0 "" 2
	.loc 1 40 0
@ 40 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_block_src_diff EQU #0
@ 0 "" 2
	.loc 1 41 0
@ 41 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_block_src_stride EQU #44
@ 0 "" 2
	.loc 1 43 0
@ 43 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_blockd_predictor EQU #8
@ 0 "" 2
	.loc 1 46 0
@ 46 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_writer_lowvalue EQU #0
@ 0 "" 2
	.loc 1 47 0
@ 47 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_writer_range EQU #4
@ 0 "" 2
	.loc 1 48 0
@ 48 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_writer_count EQU #8
@ 0 "" 2
	.loc 1 49 0
@ 49 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_writer_pos EQU #12
@ 0 "" 2
	.loc 1 50 0
@ 50 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_writer_buffer EQU #16
@ 0 "" 2
	.loc 1 51 0
@ 51 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_writer_buffer_end EQU #20
@ 0 "" 2
	.loc 1 52 0
@ 52 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_writer_error EQU #24
@ 0 "" 2
	.loc 1 54 0
@ 54 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
tokenextra_token EQU #6
@ 0 "" 2
	.loc 1 55 0
@ 55 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
tokenextra_extra EQU #4
@ 0 "" 2
	.loc 1 56 0
@ 56 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
tokenextra_context_tree EQU #0
@ 0 "" 2
	.loc 1 57 0
@ 57 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
tokenextra_skip_eob_node EQU #7
@ 0 "" 2
	.loc 1 58 0
@ 58 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
TOKENEXTRA_SZ EQU #8
@ 0 "" 2
	.loc 1 60 0
@ 60 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_extra_bit_struct_sz EQU #16
@ 0 "" 2
	.loc 1 62 0
@ 62 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_token_value EQU #0
@ 0 "" 2
	.loc 1 63 0
@ 63 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_token_len EQU #4
@ 0 "" 2
	.loc 1 65 0
@ 65 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_extra_bit_struct_tree EQU #0
@ 0 "" 2
	.loc 1 66 0
@ 66 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_extra_bit_struct_prob EQU #4
@ 0 "" 2
	.loc 1 67 0
@ 67 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_extra_bit_struct_len EQU #8
@ 0 "" 2
	.loc 1 68 0
@ 68 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_extra_bit_struct_base_val EQU #12
@ 0 "" 2
	.loc 1 70 0
@ 70 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_comp_tplist EQU #139872
@ 0 "" 2
	.loc 1 71 0
@ 71 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_comp_common EQU #105664
@ 0 "" 2
	.loc 1 72 0
@ 72 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_comp_bc EQU #110880
@ 0 "" 2
	.loc 1 73 0
@ 73 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_writer_sz EQU #36
@ 0 "" 2
	.loc 1 75 0
@ 75 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
tokenlist_start EQU #0
@ 0 "" 2
	.loc 1 76 0
@ 76 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
tokenlist_stop EQU #4
@ 0 "" 2
	.loc 1 77 0
@ 77 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
TOKENLIST_SZ EQU #8
@ 0 "" 2
	.loc 1 79 0
@ 79 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c" 1
	
vp8_common_mb_rows EQU #2424
@ 0 "" 2
	.loc 1 81 0
	mov	r0, #0
	bx	lr
	.cfi_endproc
.LFE99:
	.size	main, .-main
	.text
.Letext0:
	.file 2 "/Users/macbookair/linphone-android/submodules/externals/libvpx/vp8/encoder/boolhuff.h"
	.file 3 "/Users/macbookair/android-ndk/platforms/android-19/arch-arm/usr/include/unistd.h"
	.section	.debug_info,"",%progbits
.Ldebug_info0:
	.4byte	0xf8
	.2byte	0x2
	.4byte	.Ldebug_abbrev0
	.byte	0x4
	.uleb128 0x1
	.4byte	.LASF15
	.byte	0x1
	.4byte	.LASF16
	.4byte	.LASF17
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
	.uleb128 0x3
	.byte	0x8
	.byte	0x4
	.4byte	.LASF10
	.uleb128 0x4
	.4byte	0x46
	.4byte	0x95
	.uleb128 0x5
	.4byte	0x38
	.byte	0xff
	.byte	0
	.uleb128 0x6
	.byte	0x1
	.4byte	.LASF18
	.byte	0x1
	.byte	0x14
	.byte	0x1
	.4byte	0x31
	.4byte	.LFB99
	.4byte	.LFE99
	.byte	0x2
	.byte	0x7d
	.sleb128 0
	.uleb128 0x4
	.4byte	0x38
	.4byte	0xbd
	.uleb128 0x5
	.4byte	0x38
	.byte	0xff
	.byte	0
	.uleb128 0x7
	.4byte	.LASF11
	.byte	0x2
	.byte	0x2c
	.4byte	0xca
	.byte	0x1
	.byte	0x1
	.uleb128 0x8
	.4byte	0xad
	.uleb128 0x7
	.4byte	.LASF12
	.byte	0x2
	.byte	0x2f
	.4byte	0xdc
	.byte	0x1
	.byte	0x1
	.uleb128 0x8
	.4byte	0x85
	.uleb128 0x7
	.4byte	.LASF13
	.byte	0x3
	.byte	0xaf
	.4byte	0x38
	.byte	0x1
	.byte	0x1
	.uleb128 0x7
	.4byte	.LASF14
	.byte	0x3
	.byte	0xb3
	.4byte	0x38
	.byte	0x1
	.byte	0x1
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
	.uleb128 0x1
	.byte	0x1
	.uleb128 0x49
	.uleb128 0x13
	.uleb128 0x1
	.uleb128 0x13
	.byte	0
	.byte	0
	.uleb128 0x5
	.uleb128 0x21
	.byte	0
	.uleb128 0x49
	.uleb128 0x13
	.uleb128 0x2f
	.uleb128 0xb
	.byte	0
	.byte	0
	.uleb128 0x6
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
	.uleb128 0x7
	.uleb128 0x34
	.byte	0
	.uleb128 0x3
	.uleb128 0xe
	.uleb128 0x3a
	.uleb128 0xb
	.uleb128 0x3b
	.uleb128 0xb
	.uleb128 0x49
	.uleb128 0x13
	.uleb128 0x3f
	.uleb128 0xc
	.uleb128 0x3c
	.uleb128 0xc
	.byte	0
	.byte	0
	.uleb128 0x8
	.uleb128 0x26
	.byte	0
	.uleb128 0x49
	.uleb128 0x13
	.byte	0
	.byte	0
	.byte	0
	.section	.debug_pubnames,"",%progbits
.Ldebug_pubnames0:
	.4byte	0x17
	.2byte	0x2
	.4byte	.Ldebug_info0
	.4byte	0xfc
	.4byte	0x95
	.ascii	"main\000"
	.4byte	0
	.section	.debug_pubtypes,"",%progbits
.Ldebug_pubtypes0:
	.4byte	0xd2
	.2byte	0x2
	.4byte	.Ldebug_info0
	.4byte	0xfc
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
	.4byte	0x7e
	.ascii	"double\000"
	.4byte	0
	.section	.debug_aranges,"",%progbits
	.4byte	0x1c
	.2byte	0x2
	.4byte	.Ldebug_info0
	.byte	0x4
	.byte	0
	.2byte	0
	.2byte	0
	.4byte	.LFB99
	.4byte	.LFE99-.LFB99
	.4byte	0
	.4byte	0
	.section	.debug_ranges,"",%progbits
.Ldebug_ranges0:
	.4byte	.LFB99
	.4byte	.LFE99
	.4byte	0
	.4byte	0
	.section	.debug_line,"",%progbits
.Ldebug_line0:
	.section	.debug_str,"MS",%progbits,1
.LASF5:
	.ascii	"long long int\000"
.LASF4:
	.ascii	"short unsigned int\000"
.LASF0:
	.ascii	"unsigned int\000"
.LASF18:
	.ascii	"main\000"
.LASF7:
	.ascii	"long unsigned int\000"
.LASF11:
	.ascii	"vp8_prob_cost\000"
.LASF14:
	.ascii	"__page_shift\000"
.LASF2:
	.ascii	"unsigned char\000"
.LASF12:
	.ascii	"vp8_norm\000"
.LASF8:
	.ascii	"long int\000"
.LASF6:
	.ascii	"long long unsigned int\000"
.LASF10:
	.ascii	"double\000"
.LASF15:
	.ascii	"GNU C 4.6 20120106 (prerelease)\000"
.LASF17:
	.ascii	"/Users/macbookair/linphone-android/submodules/exter"
	.ascii	"nals/build/libvpx/arm\000"
.LASF1:
	.ascii	"signed char\000"
.LASF13:
	.ascii	"__page_size\000"
.LASF3:
	.ascii	"short int\000"
.LASF16:
	.ascii	"/Users/macbookair/linphone-android/submodules/exter"
	.ascii	"nals/libvpx/vp8/encoder/vp8_asm_enc_offsets.c\000"
.LASF9:
	.ascii	"char\000"
	.ident	"GCC: (GNU) 4.6 20120106 (prerelease)"
	.section	.note.GNU-stack,"",%progbits
