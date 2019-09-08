package gama.core.lang.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import gama.core.lang.services.GamlGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalGamlParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INTEGER", "RULE_DOUBLE", "RULE_BOOLEAN", "RULE_KEYWORD", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'__synthetic__'", "'<-'", "'model'", "'import'", "'as'", "'@'", "'model:'", "';'", "'global'", "'loop'", "'if'", "'else'", "'try'", "'catch'", "'return'", "'('", "')'", "'action'", "'{'", "'}'", "'='", "'display'", "'equation'", "'solve'", "'species'", "'grid'", "'experiment'", "'ask'", "'release'", "'capture'", "'create'", "'write'", "'error'", "'warn'", "'exception'", "'save'", "'assert'", "'inspect'", "'browse'", "'draw'", "'using'", "'switch'", "'put'", "'add'", "'remove'", "'match'", "'match_between'", "'match_one'", "'parameter'", "'status'", "'highlight'", "'focus_on'", "'layout'", "'light'", "'camera'", "'image'", "'data'", "'chart'", "'agents'", "'graphics'", "'event'", "'overlay'", "'datalist'", "'do'", "'invoke'", "'init'", "'reflex'", "'aspect'", "'<<'", "'>'", "'<<+'", "'>-'", "'+<-'", "'<+'", "','", "':'", "'returns:'", "'data:'", "'init:'", "'layout:'", "'image:'", "'parameter:'", "'aspect:'", "'light:'", "'as:'", "'species:'", "'action:'", "'on_change:'", "'->'", "'::'", "'?'", "'or'", "'and'", "'!='", "'>='", "'<='", "'<'", "'+'", "'-'", "'*'", "'/'", "'^'", "'#'", "'!'", "'not'", "'['", "']'", "'.'"
    };
    public static final int T__50=50;
    public static final int T__59=59;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=4;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=10;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_KEYWORD=9;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int T__90=90;
    public static final int RULE_BOOLEAN=8;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__99=99;
    public static final int T__14=14;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__70=70;
    public static final int T__121=121;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__120=120;
    public static final int RULE_STRING=5;
    public static final int RULE_SL_COMMENT=11;
    public static final int RULE_DOUBLE=7;
    public static final int T__77=77;
    public static final int T__119=119;
    public static final int T__78=78;
    public static final int T__118=118;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int T__115=115;
    public static final int EOF=-1;
    public static final int T__74=74;
    public static final int T__114=114;
    public static final int T__75=75;
    public static final int T__117=117;
    public static final int T__76=76;
    public static final int T__116=116;
    public static final int T__80=80;
    public static final int T__111=111;
    public static final int T__81=81;
    public static final int T__110=110;
    public static final int T__82=82;
    public static final int T__113=113;
    public static final int T__83=83;
    public static final int T__112=112;
    public static final int RULE_WS=12;
    public static final int RULE_ANY_OTHER=13;
    public static final int T__88=88;
    public static final int T__108=108;
    public static final int T__89=89;
    public static final int T__107=107;
    public static final int T__109=109;
    public static final int T__84=84;
    public static final int T__104=104;
    public static final int T__85=85;
    public static final int T__103=103;
    public static final int RULE_INTEGER=6;
    public static final int T__86=86;
    public static final int T__106=106;
    public static final int T__87=87;
    public static final int T__105=105;


        public InternalGamlParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalGamlParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[366+1];
             
             
        }
        

    public String[] getTokenNames() { return InternalGamlParser.tokenNames; }
    public String getGrammarFileName() { return "InternalGaml.g"; }



    /*
      This grammar contains a lot of empty actions to work around a bug in ANTLR.
      Otherwise the ANTLR tool will create synpreds that cannot be compiled in some rare cases.
    */

     	private GamlGrammarAccess grammarAccess;

        public InternalGamlParser(TokenStream input, GamlGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Entry";
       	}

       	@Override
       	protected GamlGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}
    public final EObject entryRuleEntry() throws RecognitionException {
        EObject current = null;
        int entryRuleEntry_StartIndex = input.index();
        EObject iv_ruleEntry = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getEntryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleEntry=ruleEntry();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleEntry; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 1, entryRuleEntry_StartIndex); }
        }
        return current;
    }
    public final EObject ruleEntry() throws RecognitionException {
        EObject current = null;
        int ruleEntry_StartIndex = input.index();
        EObject this_Model_0 = null;

        EObject this_StringEvaluator_1 = null;

        EObject this_StandaloneBlock_2 = null;

        EObject this_ExperimentFileStructure_3 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return current; }
            {
            int alt1=4;
            switch ( input.LA(1) ) {
            case 16:
            case 19:
                {
                alt1=1;
                }
                break;
            case RULE_ID:
                {
                alt1=2;
                }
                break;
            case 14:
                {
                alt1=3;
                }
                break;
            case 40:
                {
                alt1=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    {
                    {
                    if ( state.backtracking==0 ) {

                      				/* */
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getEntryAccess().getModelParserRuleCall_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_Model_0=ruleModel();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_Model_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEntryAccess().getStringEvaluatorParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_StringEvaluator_1=ruleStringEvaluator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_StringEvaluator_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEntryAccess().getStandaloneBlockParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_StandaloneBlock_2=ruleStandaloneBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_StandaloneBlock_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEntryAccess().getExperimentFileStructureParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ExperimentFileStructure_3=ruleExperimentFileStructure();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ExperimentFileStructure_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 2, ruleEntry_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleStandaloneBlock() throws RecognitionException {
        EObject current = null;
        int entryRuleStandaloneBlock_StartIndex = input.index();
        EObject iv_ruleStandaloneBlock = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStandaloneBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStandaloneBlock=ruleStandaloneBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStandaloneBlock; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 3, entryRuleStandaloneBlock_StartIndex); }
        }
        return current;
    }
    public final EObject ruleStandaloneBlock() throws RecognitionException {
        EObject current = null;
        int ruleStandaloneBlock_StartIndex = input.index();
        Token otherlv_0=null;
        EObject lv_block_1_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return current; }
            {
            {
            otherlv_0=(Token)match(input,14,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getStandaloneBlockAccess().get__synthetic__Keyword_0());
              		
            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getStandaloneBlockAccess().getBlockBlockParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_block_1_0=ruleBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getStandaloneBlockRule());
              					}
              					set(
              						current,
              						"block",
              						lv_block_1_0,
              						"gama.core.lang.Gaml.Block");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 4, ruleStandaloneBlock_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleStringEvaluator() throws RecognitionException {
        EObject current = null;
        int entryRuleStringEvaluator_StartIndex = input.index();
        EObject iv_ruleStringEvaluator = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStringEvaluatorRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStringEvaluator=ruleStringEvaluator();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStringEvaluator; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 5, entryRuleStringEvaluator_StartIndex); }
        }
        return current;
    }
    public final EObject ruleStringEvaluator() throws RecognitionException {
        EObject current = null;
        int ruleStringEvaluator_StartIndex = input.index();
        Token lv_toto_0_0=null;
        Token otherlv_1=null;
        EObject lv_expr_2_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return current; }
            {
            {
            {
            {
            lv_toto_0_0=(Token)match(input,RULE_ID,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_toto_0_0, grammarAccess.getStringEvaluatorAccess().getTotoIDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getStringEvaluatorRule());
              					}
              					setWithLastConsumed(
              						current,
              						"toto",
              						lv_toto_0_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }

            otherlv_1=(Token)match(input,15,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getStringEvaluatorAccess().getLessThanSignHyphenMinusKeyword_1());
              		
            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getStringEvaluatorAccess().getExprExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_expr_2_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getStringEvaluatorRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_2_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 6, ruleStringEvaluator_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;
        int entryRuleModel_StartIndex = input.index();
        EObject iv_ruleModel = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getModelRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleModel=ruleModel();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleModel; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 7, entryRuleModel_StartIndex); }
        }
        return current;
    }
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;
        int ruleModel_StartIndex = input.index();
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        EObject lv_pragmas_0_0 = null;

        EObject lv_imports_3_0 = null;

        EObject lv_block_4_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return current; }
            {
            {
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==19) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getModelAccess().getPragmasPragmaParserRuleCall_0_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_pragmas_0_0=rulePragma();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getModelRule());
            	      					}
            	      					add(
            	      						current,
            	      						"pragmas",
            	      						lv_pragmas_0_0,
            	      						"gama.core.lang.Gaml.Pragma");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            otherlv_1=(Token)match(input,16,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getModelAccess().getModelKeyword_1());
              		
            }
            {
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_2_0, grammarAccess.getModelAccess().getNameIDTerminalRuleCall_2_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getModelRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_2_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==17) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getModelAccess().getImportsImportParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_8);
            	    lv_imports_3_0=ruleImport();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getModelRule());
            	      					}
            	      					add(
            	      						current,
            	      						"imports",
            	      						lv_imports_3_0,
            	      						"gama.core.lang.Gaml.Import");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getModelAccess().getBlockModelBlockParserRuleCall_4_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_block_4_0=ruleModelBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getModelRule());
              					}
              					set(
              						current,
              						"block",
              						lv_block_4_0,
              						"gama.core.lang.Gaml.ModelBlock");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 8, ruleModel_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleModelBlock() throws RecognitionException {
        EObject current = null;
        int entryRuleModelBlock_StartIndex = input.index();
        EObject iv_ruleModelBlock = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getModelBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleModelBlock=ruleModelBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleModelBlock; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 9, entryRuleModelBlock_StartIndex); }
        }
        return current;
    }
    public final EObject ruleModelBlock() throws RecognitionException {
        EObject current = null;
        int ruleModelBlock_StartIndex = input.index();
        EObject lv_statements_1_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return current; }
            {
            {
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getModelBlockAccess().getBlockAction_0(),
              					current);
              			
            }

            }
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==22||(LA4_0>=38 && LA4_0<=40)) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getModelBlockAccess().getStatementsS_SectionParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_statements_1_0=ruleS_Section();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getModelBlockRule());
            	      					}
            	      					add(
            	      						current,
            	      						"statements",
            	      						lv_statements_1_0,
            	      						"gama.core.lang.Gaml.S_Section");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 10, ruleModelBlock_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleImport() throws RecognitionException {
        EObject current = null;
        int entryRuleImport_StartIndex = input.index();
        EObject iv_ruleImport = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getImportRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleImport=ruleImport();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleImport; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 11, entryRuleImport_StartIndex); }
        }
        return current;
    }
    public final EObject ruleImport() throws RecognitionException {
        EObject current = null;
        int ruleImport_StartIndex = input.index();
        Token otherlv_0=null;
        Token lv_importURI_1_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_name_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return current; }
            {
            {
            otherlv_0=(Token)match(input,17,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getImportAccess().getImportKeyword_0());
              		
            }
            {
            {
            lv_importURI_1_0=(Token)match(input,RULE_STRING,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_importURI_1_0, grammarAccess.getImportAccess().getImportURISTRINGTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getImportRule());
              					}
              					setWithLastConsumed(
              						current,
              						"importURI",
              						lv_importURI_1_0,
              						"gama.core.lang.Gaml.STRING");
              				
            }

            }


            }
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==18) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    {
                    otherlv_2=(Token)match(input,18,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getImportAccess().getAsKeyword_2_0());
                      			
                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getImportAccess().getNameValid_IDParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_name_3_0=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getImportRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_3_0,
                      							"gama.core.lang.Gaml.Valid_ID");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 12, ruleImport_StartIndex); }
        }
        return current;
    }
    public final EObject entryRulePragma() throws RecognitionException {
        EObject current = null;
        int entryRulePragma_StartIndex = input.index();
        EObject iv_rulePragma = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPragmaRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePragma=rulePragma();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePragma; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 13, entryRulePragma_StartIndex); }
        }
        return current;
    }
    public final EObject rulePragma() throws RecognitionException {
        EObject current = null;
        int rulePragma_StartIndex = input.index();
        Token otherlv_0=null;
        Token lv_name_1_0=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return current; }
            {
            {
            otherlv_0=(Token)match(input,19,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getPragmaAccess().getCommercialAtKeyword_0());
              		
            }
            {
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getPragmaAccess().getNameIDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getPragmaRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 14, rulePragma_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleExperimentFileStructure() throws RecognitionException {
        EObject current = null;
        int entryRuleExperimentFileStructure_StartIndex = input.index();
        EObject iv_ruleExperimentFileStructure = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExperimentFileStructureRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExperimentFileStructure=ruleExperimentFileStructure();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExperimentFileStructure; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 15, entryRuleExperimentFileStructure_StartIndex); }
        }
        return current;
    }
    public final EObject ruleExperimentFileStructure() throws RecognitionException {
        EObject current = null;
        int ruleExperimentFileStructure_StartIndex = input.index();
        EObject lv_exp_0_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return current; }
            {
            {
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getExperimentFileStructureAccess().getExpHeadlessExperimentParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_exp_0_0=ruleHeadlessExperiment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getExperimentFileStructureRule());
              				}
              				set(
              					current,
              					"exp",
              					lv_exp_0_0,
              					"gama.core.lang.Gaml.HeadlessExperiment");
              				afterParserOrEnumRuleCall();
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 16, ruleExperimentFileStructure_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleHeadlessExperiment() throws RecognitionException {
        EObject current = null;
        int entryRuleHeadlessExperiment_StartIndex = input.index();
        EObject iv_ruleHeadlessExperiment = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getHeadlessExperimentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleHeadlessExperiment=ruleHeadlessExperiment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleHeadlessExperiment; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 17, entryRuleHeadlessExperiment_StartIndex); }
        }
        return current;
    }
    public final EObject ruleHeadlessExperiment() throws RecognitionException {
        EObject current = null;
        int ruleHeadlessExperiment_StartIndex = input.index();
        Token lv_name_1_2=null;
        Token otherlv_2=null;
        Token lv_importURI_3_0=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_1 = null;

        EObject lv_facets_4_0 = null;

        EObject lv_block_5_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getHeadlessExperimentAccess().getKey_ExperimentKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_13);
            lv_key_0_0=rule_ExperimentKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getHeadlessExperimentRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._ExperimentKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_ID||LA6_0==36||(LA6_0>=38 && LA6_0<=81)) ) {
                alt6=1;
            }
            else if ( (LA6_0==RULE_STRING) ) {
                alt6=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getHeadlessExperimentAccess().getNameValid_IDParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_14);
                    lv_name_1_1=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getHeadlessExperimentRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_1_1,
                      							"gama.core.lang.Gaml.Valid_ID");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 2 :
                    {
                    lv_name_1_2=(Token)match(input,RULE_STRING,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_1_2, grammarAccess.getHeadlessExperimentAccess().getNameSTRINGTerminalRuleCall_1_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getHeadlessExperimentRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_1_2,
                      							"gama.core.lang.Gaml.STRING");
                      					
                    }

                    }
                    break;

            }


            }


            }
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==20) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    {
                    otherlv_2=(Token)match(input,20,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getHeadlessExperimentAccess().getModelKeyword_2_0());
                      			
                    }
                    {
                    {
                    lv_importURI_3_0=(Token)match(input,RULE_STRING,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_importURI_3_0, grammarAccess.getHeadlessExperimentAccess().getImportURISTRINGTerminalRuleCall_2_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getHeadlessExperimentRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"importURI",
                      							lv_importURI_3_0,
                      							"gama.core.lang.Gaml.STRING");
                      					
                    }

                    }


                    }


                    }
                    break;

            }
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==RULE_ID||LA8_0==15||(LA8_0>=90 && LA8_0<=102)) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getHeadlessExperimentAccess().getFacetsFacetParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_4_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getHeadlessExperimentRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_4_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==32) ) {
                alt9=1;
            }
            else if ( (LA9_0==21) ) {
                alt9=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getHeadlessExperimentAccess().getBlockBlockParserRuleCall_4_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_5_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getHeadlessExperimentRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_5_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_6=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getHeadlessExperimentAccess().getSemicolonKeyword_4_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 18, ruleHeadlessExperiment_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Section() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Section_StartIndex = input.index();
        EObject iv_ruleS_Section = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_SectionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Section=ruleS_Section();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Section; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 19, entryRuleS_Section_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Section() throws RecognitionException {
        EObject current = null;
        int ruleS_Section_StartIndex = input.index();
        EObject this_S_Global_0 = null;

        EObject this_S_Species_1 = null;

        EObject this_S_Experiment_2 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return current; }
            {
            int alt10=3;
            switch ( input.LA(1) ) {
            case 22:
                {
                alt10=1;
                }
                break;
            case 38:
            case 39:
                {
                alt10=2;
                }
                break;
            case 40:
                {
                alt10=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_SectionAccess().getS_GlobalParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Global_0=ruleS_Global();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Global_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_SectionAccess().getS_SpeciesParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Species_1=ruleS_Species();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Species_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_SectionAccess().getS_ExperimentParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Experiment_2=ruleS_Experiment();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Experiment_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 20, ruleS_Section_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Global() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Global_StartIndex = input.index();
        EObject iv_ruleS_Global = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_GlobalRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Global=ruleS_Global();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Global; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 21, entryRuleS_Global_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Global() throws RecognitionException {
        EObject current = null;
        int ruleS_Global_StartIndex = input.index();
        Token lv_key_0_0=null;
        Token otherlv_3=null;
        EObject lv_facets_1_0 = null;

        EObject lv_block_2_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return current; }
            {
            {
            {
            {
            lv_key_0_0=(Token)match(input,22,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_GlobalAccess().getKeyGlobalKeyword_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_GlobalRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_0_0, "global");
              				
            }

            }


            }
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==RULE_ID||LA11_0==15||(LA11_0>=90 && LA11_0<=102)) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_GlobalAccess().getFacetsFacetParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_1_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_GlobalRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_1_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==32) ) {
                alt12=1;
            }
            else if ( (LA12_0==21) ) {
                alt12=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_GlobalAccess().getBlockBlockParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_2_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_GlobalRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_2_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_3=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getS_GlobalAccess().getSemicolonKeyword_2_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 22, ruleS_Global_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Species() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Species_StartIndex = input.index();
        EObject iv_ruleS_Species = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_SpeciesRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Species=ruleS_Species();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Species; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 23, entryRuleS_Species_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Species() throws RecognitionException {
        EObject current = null;
        int ruleS_Species_StartIndex = input.index();
        Token lv_name_1_0=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_SpeciesAccess().getKey_SpeciesKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_7);
            lv_key_0_0=rule_SpeciesKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_SpeciesRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._SpeciesKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getS_SpeciesAccess().getNameIDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_SpeciesRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==RULE_ID||LA13_0==15||(LA13_0>=90 && LA13_0<=102)) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_SpeciesAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_SpeciesRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==32) ) {
                alt14=1;
            }
            else if ( (LA14_0==21) ) {
                alt14=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_SpeciesAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_SpeciesRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_SpeciesAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 24, ruleS_Species_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Experiment() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Experiment_StartIndex = input.index();
        EObject iv_ruleS_Experiment = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_ExperimentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Experiment=ruleS_Experiment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Experiment; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 25, entryRuleS_Experiment_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Experiment() throws RecognitionException {
        EObject current = null;
        int ruleS_Experiment_StartIndex = input.index();
        Token lv_name_1_2=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_1 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_ExperimentAccess().getKey_ExperimentKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_13);
            lv_key_0_0=rule_ExperimentKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_ExperimentRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._ExperimentKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==RULE_ID||LA15_0==36||(LA15_0>=38 && LA15_0<=81)) ) {
                alt15=1;
            }
            else if ( (LA15_0==RULE_STRING) ) {
                alt15=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_ExperimentAccess().getNameValid_IDParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_15);
                    lv_name_1_1=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_ExperimentRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_1_1,
                      							"gama.core.lang.Gaml.Valid_ID");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 2 :
                    {
                    lv_name_1_2=(Token)match(input,RULE_STRING,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_1_2, grammarAccess.getS_ExperimentAccess().getNameSTRINGTerminalRuleCall_1_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getS_ExperimentRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_1_2,
                      							"gama.core.lang.Gaml.STRING");
                      					
                    }

                    }
                    break;

            }


            }


            }
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==RULE_ID||LA16_0==15||(LA16_0>=90 && LA16_0<=102)) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_ExperimentAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_ExperimentRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==32) ) {
                alt17=1;
            }
            else if ( (LA17_0==21) ) {
                alt17=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_ExperimentAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_ExperimentRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_ExperimentAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 26, ruleS_Experiment_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;
        int entryRuleStatement_StartIndex = input.index();
        EObject iv_ruleStatement = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStatement=ruleStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 27, entryRuleStatement_StartIndex); }
        }
        return current;
    }
    public final EObject ruleStatement() throws RecognitionException {
        EObject current = null;
        int ruleStatement_StartIndex = input.index();
        EObject this_S_Declaration_0 = null;

        EObject this_S_Assignment_1 = null;

        EObject this_S_1Expr_Facets_BlockOrEnd_2 = null;

        EObject this_S_Other_3 = null;

        EObject this_S_Do_4 = null;

        EObject this_S_Return_5 = null;

        EObject this_S_Solve_6 = null;

        EObject this_S_If_7 = null;

        EObject this_S_Try_8 = null;

        EObject this_S_Equations_9 = null;

        EObject this_S_Display_10 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return current; }
            {
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( ((LA20_0>=RULE_ID && LA20_0<=RULE_KEYWORD)||(LA20_0>=23 && LA20_0<=24)||LA20_0==26||(LA20_0>=28 && LA20_0<=29)||(LA20_0>=31 && LA20_0<=32)||(LA20_0>=36 && LA20_0<=81)||LA20_0==112||(LA20_0>=116 && LA20_0<=119)) ) {
                alt20=1;
            }
            else if ( (LA20_0==35) ) {
                alt20=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    {
                    int alt19=2;
                    alt19 = dfa19.predict(input);
                    switch (alt19) {
                        case 1 :
                            {
                            {
                            if ( state.backtracking==0 ) {

                              					/* */
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getStatementAccess().getS_DeclarationParserRuleCall_0_0());
                              				
                            }
                            pushFollow(FOLLOW_2);
                            this_S_Declaration_0=ruleS_Declaration();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_S_Declaration_0;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }


                            }
                            break;
                        case 2 :
                            {
                            int alt18=9;
                            alt18 = dfa18.predict(input);
                            switch (alt18) {
                                case 1 :
                                    {
                                    {
                                    if ( state.backtracking==0 ) {

                                      						/* */
                                      					
                                    }
                                    if ( state.backtracking==0 ) {

                                      						newCompositeNode(grammarAccess.getStatementAccess().getS_AssignmentParserRuleCall_0_1_0());
                                      					
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Assignment_1=ruleS_Assignment();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						current = this_S_Assignment_1;
                                      						afterParserOrEnumRuleCall();
                                      					
                                    }

                                    }


                                    }
                                    break;
                                case 2 :
                                    {
                                    if ( state.backtracking==0 ) {

                                      					/* */
                                      				
                                    }
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_1Expr_Facets_BlockOrEndParserRuleCall_0_1_1());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_1Expr_Facets_BlockOrEnd_2=ruleS_1Expr_Facets_BlockOrEnd();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_1Expr_Facets_BlockOrEnd_2;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 3 :
                                    {
                                    if ( state.backtracking==0 ) {

                                      					/* */
                                      				
                                    }
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_OtherParserRuleCall_0_1_2());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Other_3=ruleS_Other();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_Other_3;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 4 :
                                    {
                                    if ( state.backtracking==0 ) {

                                      					/* */
                                      				
                                    }
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_DoParserRuleCall_0_1_3());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Do_4=ruleS_Do();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_Do_4;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 5 :
                                    {
                                    if ( state.backtracking==0 ) {

                                      					/* */
                                      				
                                    }
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_ReturnParserRuleCall_0_1_4());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Return_5=ruleS_Return();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_Return_5;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 6 :
                                    {
                                    if ( state.backtracking==0 ) {

                                      					/* */
                                      				
                                    }
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_SolveParserRuleCall_0_1_5());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Solve_6=ruleS_Solve();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_Solve_6;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 7 :
                                    {
                                    if ( state.backtracking==0 ) {

                                      					/* */
                                      				
                                    }
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_IfParserRuleCall_0_1_6());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_If_7=ruleS_If();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_If_7;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 8 :
                                    {
                                    if ( state.backtracking==0 ) {

                                      					/* */
                                      				
                                    }
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_TryParserRuleCall_0_1_7());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Try_8=ruleS_Try();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_Try_8;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 9 :
                                    {
                                    if ( state.backtracking==0 ) {

                                      					/* */
                                      				
                                    }
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_EquationsParserRuleCall_0_1_8());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Equations_9=ruleS_Equations();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_Equations_9;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getStatementAccess().getS_DisplayParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Display_10=ruleS_Display();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Display_10;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 28, ruleStatement_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_1Expr_Facets_BlockOrEnd() throws RecognitionException {
        EObject current = null;
        int entryRuleS_1Expr_Facets_BlockOrEnd_StartIndex = input.index();
        EObject iv_ruleS_1Expr_Facets_BlockOrEnd = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_1Expr_Facets_BlockOrEndRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_1Expr_Facets_BlockOrEnd=ruleS_1Expr_Facets_BlockOrEnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_1Expr_Facets_BlockOrEnd; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 29, entryRuleS_1Expr_Facets_BlockOrEnd_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_1Expr_Facets_BlockOrEnd() throws RecognitionException {
        EObject current = null;
        int ruleS_1Expr_Facets_BlockOrEnd_StartIndex = input.index();
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        EObject lv_expr_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_1Expr_Facets_BlockOrEndAccess().getKey_1Expr_Facets_BlockOrEnd_KeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_5);
            lv_key_0_0=rule_1Expr_Facets_BlockOrEnd_Key();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_1Expr_Facets_BlockOrEndRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._1Expr_Facets_BlockOrEnd_Key");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_1Expr_Facets_BlockOrEndAccess().getExprExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_expr_1_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_1Expr_Facets_BlockOrEndRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_1_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==RULE_ID||LA21_0==15||(LA21_0>=90 && LA21_0<=102)) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_1Expr_Facets_BlockOrEndAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_1Expr_Facets_BlockOrEndRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==32) ) {
                alt22=1;
            }
            else if ( (LA22_0==21) ) {
                alt22=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_1Expr_Facets_BlockOrEndAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_1Expr_Facets_BlockOrEndRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_1Expr_Facets_BlockOrEndAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 30, ruleS_1Expr_Facets_BlockOrEnd_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Do() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Do_StartIndex = input.index();
        EObject iv_ruleS_Do = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_DoRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Do=ruleS_Do();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Do; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 31, entryRuleS_Do_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Do() throws RecognitionException {
        EObject current = null;
        int ruleS_Do_StartIndex = input.index();
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        EObject lv_expr_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 32) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_DoAccess().getKey_DoKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_12);
            lv_key_0_0=rule_DoKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_DoRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._DoKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_DoAccess().getExprAbstractRefParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_expr_1_0=ruleAbstractRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_DoRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_1_0,
              						"gama.core.lang.Gaml.AbstractRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==RULE_ID||LA23_0==15||(LA23_0>=90 && LA23_0<=102)) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_DoAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_DoRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==32) ) {
                alt24=1;
            }
            else if ( (LA24_0==21) ) {
                alt24=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_DoAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_DoRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_DoAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 32, ruleS_Do_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Loop() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Loop_StartIndex = input.index();
        EObject iv_ruleS_Loop = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 33) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_LoopRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Loop=ruleS_Loop();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Loop; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 33, entryRuleS_Loop_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Loop() throws RecognitionException {
        EObject current = null;
        int ruleS_Loop_StartIndex = input.index();
        Token lv_key_0_0=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 34) ) { return current; }
            {
            {
            {
            {
            lv_key_0_0=(Token)match(input,23,FOLLOW_16); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_LoopAccess().getKeyLoopKeyword_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_LoopRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_0_0, "loop");
              				
            }

            }


            }
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==36||(LA25_0>=38 && LA25_0<=81)) ) {
                alt25=1;
            }
            else if ( (LA25_0==RULE_ID) ) {
                int LA25_2 = input.LA(2);

                if ( (LA25_2==RULE_ID||LA25_2==15||LA25_2==32||(LA25_2>=90 && LA25_2<=102)) ) {
                    alt25=1;
                }
            }
            switch (alt25) {
                case 1 :
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getS_LoopAccess().getNameValid_IDParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_17);
                    lv_name_1_0=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getS_LoopRule());
                      					}
                      					set(
                      						current,
                      						"name",
                      						lv_name_1_0,
                      						"gama.core.lang.Gaml.Valid_ID");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==RULE_ID||LA26_0==15||(LA26_0>=90 && LA26_0<=102)) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_LoopAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_17);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_LoopRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_LoopAccess().getBlockBlockParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_block_3_0=ruleBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_LoopRule());
              					}
              					set(
              						current,
              						"block",
              						lv_block_3_0,
              						"gama.core.lang.Gaml.Block");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 34, ruleS_Loop_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_If() throws RecognitionException {
        EObject current = null;
        int entryRuleS_If_StartIndex = input.index();
        EObject iv_ruleS_If = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 35) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_IfRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_If=ruleS_If();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_If; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 35, entryRuleS_If_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_If() throws RecognitionException {
        EObject current = null;
        int ruleS_If_StartIndex = input.index();
        Token lv_key_0_0=null;
        Token otherlv_3=null;
        EObject lv_expr_1_0 = null;

        EObject lv_block_2_0 = null;

        EObject lv_else_4_1 = null;

        EObject lv_else_4_2 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 36) ) { return current; }
            {
            {
            {
            {
            lv_key_0_0=(Token)match(input,24,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_IfAccess().getKeyIfKeyword_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_IfRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_0_0, "if");
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_IfAccess().getExprExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_3);
            lv_expr_1_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_IfRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_1_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_IfAccess().getBlockBlockParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_18);
            lv_block_2_0=ruleBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_IfRule());
              					}
              					set(
              						current,
              						"block",
              						lv_block_2_0,
              						"gama.core.lang.Gaml.Block");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==25) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    {
                    {
                    otherlv_3=(Token)match(input,25,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getS_IfAccess().getElseKeyword_3_0());
                      				
                    }

                    }
                    {
                    {
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==24) ) {
                        alt27=1;
                    }
                    else if ( (LA27_0==32) ) {
                        alt27=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 27, 0, input);

                        throw nvae;
                    }
                    switch (alt27) {
                        case 1 :
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getS_IfAccess().getElseS_IfParserRuleCall_3_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_2);
                            lv_else_4_1=ruleS_If();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getS_IfRule());
                              							}
                              							set(
                              								current,
                              								"else",
                              								lv_else_4_1,
                              								"gama.core.lang.Gaml.S_If");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }
                            break;
                        case 2 :
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getS_IfAccess().getElseBlockParserRuleCall_3_1_0_1());
                              						
                            }
                            pushFollow(FOLLOW_2);
                            lv_else_4_2=ruleBlock();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getS_IfRule());
                              							}
                              							set(
                              								current,
                              								"else",
                              								lv_else_4_2,
                              								"gama.core.lang.Gaml.Block");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 36, ruleS_If_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Try() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Try_StartIndex = input.index();
        EObject iv_ruleS_Try = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 37) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_TryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Try=ruleS_Try();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Try; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 37, entryRuleS_Try_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Try() throws RecognitionException {
        EObject current = null;
        int ruleS_Try_StartIndex = input.index();
        Token lv_key_0_0=null;
        Token otherlv_2=null;
        EObject lv_block_1_0 = null;

        EObject lv_catch_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 38) ) { return current; }
            {
            {
            {
            {
            lv_key_0_0=(Token)match(input,26,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_TryAccess().getKeyTryKeyword_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_TryRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_0_0, "try");
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_TryAccess().getBlockBlockParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_20);
            lv_block_1_0=ruleBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_TryRule());
              					}
              					set(
              						current,
              						"block",
              						lv_block_1_0,
              						"gama.core.lang.Gaml.Block");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==27) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    {
                    {
                    otherlv_2=(Token)match(input,27,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_2, grammarAccess.getS_TryAccess().getCatchKeyword_2_0());
                      				
                    }

                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_TryAccess().getCatchBlockParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_catch_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_TryRule());
                      						}
                      						set(
                      							current,
                      							"catch",
                      							lv_catch_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 38, ruleS_Try_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Other() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Other_StartIndex = input.index();
        EObject iv_ruleS_Other = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 39) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_OtherRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Other=ruleS_Other();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Other; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 39, entryRuleS_Other_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Other() throws RecognitionException {
        EObject current = null;
        int ruleS_Other_StartIndex = input.index();
        Token lv_key_0_0=null;
        Token otherlv_3=null;
        EObject lv_facets_1_0 = null;

        EObject lv_block_2_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 40) ) { return current; }
            {
            {
            {
            {
            lv_key_0_0=(Token)match(input,RULE_ID,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_OtherAccess().getKeyIDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_OtherRule());
              					}
              					setWithLastConsumed(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==RULE_ID||LA30_0==15||(LA30_0>=90 && LA30_0<=102)) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_OtherAccess().getFacetsFacetParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_1_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_OtherRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_1_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==32) ) {
                alt31=1;
            }
            else if ( (LA31_0==21) ) {
                alt31=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_OtherAccess().getBlockBlockParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_2_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_OtherRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_2_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_3=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getS_OtherAccess().getSemicolonKeyword_2_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 40, ruleS_Other_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Return() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Return_StartIndex = input.index();
        EObject iv_ruleS_Return = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 41) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_ReturnRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Return=ruleS_Return();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Return; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 41, entryRuleS_Return_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Return() throws RecognitionException {
        EObject current = null;
        int ruleS_Return_StartIndex = input.index();
        Token lv_key_0_0=null;
        Token otherlv_2=null;
        EObject lv_expr_1_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 42) ) { return current; }
            {
            {
            {
            {
            lv_key_0_0=(Token)match(input,28,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_ReturnAccess().getKeyReturnKeyword_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_ReturnRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_0_0, "return");
              				
            }

            }


            }
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( ((LA32_0>=RULE_ID && LA32_0<=RULE_KEYWORD)||LA32_0==29||LA32_0==32||LA32_0==36||(LA32_0>=38 && LA32_0<=81)||LA32_0==112||(LA32_0>=116 && LA32_0<=119)) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getS_ReturnAccess().getExprExpressionParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_22);
                    lv_expr_1_0=ruleExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getS_ReturnRule());
                      					}
                      					set(
                      						current,
                      						"expr",
                      						lv_expr_1_0,
                      						"gama.core.lang.Gaml.Expression");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getS_ReturnAccess().getSemicolonKeyword_2());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 42, ruleS_Return_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Declaration() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Declaration_StartIndex = input.index();
        EObject iv_ruleS_Declaration = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 43) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_DeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Declaration=ruleS_Declaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Declaration; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 43, entryRuleS_Declaration_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Declaration() throws RecognitionException {
        EObject current = null;
        int ruleS_Declaration_StartIndex = input.index();
        EObject this_S_Definition_0 = null;

        EObject this_S_Species_1 = null;

        EObject this_S_Reflex_2 = null;

        EObject this_S_Action_3 = null;

        EObject this_S_Loop_4 = null;

        EObject this_S_StringDefinition_5 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 44) ) { return current; }
            {
            int alt33=6;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA33_1 = input.LA(2);

                if ( (LA33_1==RULE_ID||LA33_1==36||(LA33_1>=38 && LA33_1<=81)||LA33_1==110) ) {
                    alt33=1;
                }
                else if ( (LA33_1==RULE_STRING) ) {
                    alt33=6;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 33, 1, input);

                    throw nvae;
                }
                }
                break;
            case 38:
                {
                int LA33_2 = input.LA(2);

                if ( (LA33_2==RULE_ID) ) {
                    alt33=2;
                }
                else if ( (LA33_2==110) ) {
                    alt33=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 33, 2, input);

                    throw nvae;
                }
                }
                break;
            case 39:
                {
                alt33=2;
                }
                break;
            case 79:
            case 80:
            case 81:
                {
                alt33=3;
                }
                break;
            case 31:
                {
                alt33=4;
                }
                break;
            case 23:
                {
                alt33=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;
            }

            switch (alt33) {
                case 1 :
                    {
                    {
                    if ( state.backtracking==0 ) {

                      				/* */
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getS_DeclarationAccess().getS_DefinitionParserRuleCall_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Definition_0=ruleS_Definition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_S_Definition_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_DeclarationAccess().getS_SpeciesParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Species_1=ruleS_Species();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Species_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_DeclarationAccess().getS_ReflexParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Reflex_2=ruleS_Reflex();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Reflex_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_DeclarationAccess().getS_ActionParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Action_3=ruleS_Action();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Action_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_DeclarationAccess().getS_LoopParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Loop_4=ruleS_Loop();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Loop_4;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 6 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_DeclarationAccess().getS_StringDefinitionParserRuleCall_5());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_StringDefinition_5=ruleS_StringDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_StringDefinition_5;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 44, ruleS_Declaration_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Reflex() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Reflex_StartIndex = input.index();
        EObject iv_ruleS_Reflex = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 45) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_ReflexRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Reflex=ruleS_Reflex();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Reflex; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 45, entryRuleS_Reflex_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Reflex() throws RecognitionException {
        EObject current = null;
        int ruleS_Reflex_StartIndex = input.index();
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 46) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_ReflexAccess().getKey_ReflexKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_23);
            lv_key_0_0=rule_ReflexKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_ReflexRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._ReflexKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==36||(LA34_0>=38 && LA34_0<=81)) ) {
                alt34=1;
            }
            else if ( (LA34_0==RULE_ID) ) {
                int LA34_2 = input.LA(2);

                if ( (LA34_2==RULE_ID||LA34_2==15||LA34_2==21||LA34_2==32||(LA34_2>=90 && LA34_2<=102)) ) {
                    alt34=1;
                }
            }
            switch (alt34) {
                case 1 :
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getS_ReflexAccess().getNameValid_IDParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_15);
                    lv_name_1_0=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getS_ReflexRule());
                      					}
                      					set(
                      						current,
                      						"name",
                      						lv_name_1_0,
                      						"gama.core.lang.Gaml.Valid_ID");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==RULE_ID||LA35_0==15||(LA35_0>=90 && LA35_0<=102)) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_ReflexAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_ReflexRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==32) ) {
                alt36=1;
            }
            else if ( (LA36_0==21) ) {
                alt36=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }
            switch (alt36) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_ReflexAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_ReflexRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_ReflexAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 46, ruleS_Reflex_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Definition() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Definition_StartIndex = input.index();
        EObject iv_ruleS_Definition = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 47) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_DefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Definition=ruleS_Definition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Definition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 47, entryRuleS_Definition_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Definition() throws RecognitionException {
        EObject current = null;
        int ruleS_Definition_StartIndex = input.index();
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_7=null;
        EObject lv_tkey_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_args_3_0 = null;

        EObject lv_facets_5_0 = null;

        EObject lv_block_6_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 48) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_DefinitionAccess().getTkeyTypeRefParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_12);
            lv_tkey_0_0=ruleTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_DefinitionRule());
              					}
              					set(
              						current,
              						"tkey",
              						lv_tkey_0_0,
              						"gama.core.lang.Gaml.TypeRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_DefinitionAccess().getNameValid_IDParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_24);
            lv_name_1_0=ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_DefinitionRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.Valid_ID");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==29) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    {
                    otherlv_2=(Token)match(input,29,FOLLOW_25); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getS_DefinitionAccess().getLeftParenthesisKeyword_2_0());
                      			
                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_DefinitionAccess().getArgsActionArgumentsParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_args_3_0=ruleActionArguments();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_DefinitionRule());
                      						}
                      						set(
                      							current,
                      							"args",
                      							lv_args_3_0,
                      							"gama.core.lang.Gaml.ActionArguments");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_4=(Token)match(input,30,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_DefinitionAccess().getRightParenthesisKeyword_2_2());
                      			
                    }

                    }
                    break;

            }
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==RULE_ID||LA38_0==15||(LA38_0>=90 && LA38_0<=102)) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_DefinitionAccess().getFacetsFacetParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_5_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_DefinitionRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_5_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==32) ) {
                alt39=1;
            }
            else if ( (LA39_0==21) ) {
                alt39=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }
            switch (alt39) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_DefinitionAccess().getBlockBlockParserRuleCall_4_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_6_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_DefinitionRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_6_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_7=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getS_DefinitionAccess().getSemicolonKeyword_4_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 48, ruleS_Definition_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_StringDefinition() throws RecognitionException {
        EObject current = null;
        int entryRuleS_StringDefinition_StartIndex = input.index();
        EObject iv_ruleS_StringDefinition = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 49) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_StringDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_StringDefinition=ruleS_StringDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_StringDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 49, entryRuleS_StringDefinition_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_StringDefinition() throws RecognitionException {
        EObject current = null;
        int ruleS_StringDefinition_StartIndex = input.index();
        Token lv_key_0_0=null;
        Token lv_name_1_0=null;
        Token otherlv_4=null;
        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 50) ) { return current; }
            {
            {
            {
            {
            lv_key_0_0=(Token)match(input,RULE_ID,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_StringDefinitionAccess().getKeyIDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_StringDefinitionRule());
              					}
              					setWithLastConsumed(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }
            {
            {
            lv_name_1_0=(Token)match(input,RULE_STRING,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getS_StringDefinitionAccess().getNameSTRINGTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_StringDefinitionRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.STRING");
              				
            }

            }


            }
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==RULE_ID||LA40_0==15||(LA40_0>=90 && LA40_0<=102)) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_StringDefinitionAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_StringDefinitionRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==32) ) {
                alt41=1;
            }
            else if ( (LA41_0==21) ) {
                alt41=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }
            switch (alt41) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_StringDefinitionAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_StringDefinitionRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_StringDefinitionAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 50, ruleS_StringDefinition_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Action() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Action_StartIndex = input.index();
        EObject iv_ruleS_Action = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 51) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_ActionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Action=ruleS_Action();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Action; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 51, entryRuleS_Action_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Action() throws RecognitionException {
        EObject current = null;
        int ruleS_Action_StartIndex = input.index();
        Token lv_key_1_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_args_4_0 = null;

        EObject lv_facets_6_0 = null;

        EObject lv_block_7_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 52) ) { return current; }
            {
            {
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getS_ActionAccess().getS_ActionAction_0(),
              					current);
              			
            }

            }
            {
            {
            lv_key_1_0=(Token)match(input,31,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_1_0, grammarAccess.getS_ActionAccess().getKeyActionKeyword_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_ActionRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_1_0, "action");
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_ActionAccess().getNameValid_IDParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_24);
            lv_name_2_0=ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_ActionRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_2_0,
              						"gama.core.lang.Gaml.Valid_ID");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==29) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    {
                    otherlv_3=(Token)match(input,29,FOLLOW_25); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getS_ActionAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_ActionAccess().getArgsActionArgumentsParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_args_4_0=ruleActionArguments();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_ActionRule());
                      						}
                      						set(
                      							current,
                      							"args",
                      							lv_args_4_0,
                      							"gama.core.lang.Gaml.ActionArguments");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_5=(Token)match(input,30,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getS_ActionAccess().getRightParenthesisKeyword_3_2());
                      			
                    }

                    }
                    break;

            }
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==RULE_ID||LA43_0==15||(LA43_0>=90 && LA43_0<=102)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_ActionAccess().getFacetsFacetParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_6_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_ActionRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_6_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==32) ) {
                alt44=1;
            }
            else if ( (LA44_0==21) ) {
                alt44=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }
            switch (alt44) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_ActionAccess().getBlockBlockParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_7_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_ActionRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_7_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_8=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getS_ActionAccess().getSemicolonKeyword_5_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 52, ruleS_Action_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Assignment() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Assignment_StartIndex = input.index();
        EObject iv_ruleS_Assignment = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 53) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_AssignmentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Assignment=ruleS_Assignment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Assignment; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 53, entryRuleS_Assignment_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Assignment() throws RecognitionException {
        EObject current = null;
        int ruleS_Assignment_StartIndex = input.index();
        Token otherlv_4=null;
        EObject lv_expr_0_0 = null;

        AntlrDatatypeRuleToken lv_key_1_0 = null;

        EObject lv_value_2_0 = null;

        EObject lv_facets_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 54) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_AssignmentAccess().getExprExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_27);
            lv_expr_0_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_AssignmentRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_0_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_AssignmentAccess().getKey_AssignmentKeyParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_5);
            lv_key_1_0=rule_AssignmentKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_AssignmentRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_1_0,
              						"gama.core.lang.Gaml._AssignmentKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_AssignmentAccess().getValueExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_28);
            lv_value_2_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_AssignmentRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==RULE_ID||LA45_0==15||(LA45_0>=90 && LA45_0<=102)) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_AssignmentAccess().getFacetsFacetParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_28);
            	    lv_facets_3_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_AssignmentRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_3_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

            otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getS_AssignmentAccess().getSemicolonKeyword_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 54, ruleS_Assignment_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Equations() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Equations_StartIndex = input.index();
        EObject iv_ruleS_Equations = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 55) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_EquationsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Equations=ruleS_Equations();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Equations; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 55, entryRuleS_Equations_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Equations() throws RecognitionException {
        EObject current = null;
        int ruleS_Equations_StartIndex = input.index();
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_equations_4_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 56) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_EquationsAccess().getKey_EquationsKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_12);
            lv_key_0_0=rule_EquationsKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_EquationsRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._EquationsKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_EquationsAccess().getNameValid_IDParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_name_1_0=ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_EquationsRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.Valid_ID");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==RULE_ID||LA46_0==15||(LA46_0>=90 && LA46_0<=102)) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_EquationsAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_EquationsRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==32) ) {
                alt48=1;
            }
            else if ( (LA48_0==21) ) {
                alt48=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }
            switch (alt48) {
                case 1 :
                    {
                    {
                    otherlv_3=(Token)match(input,32,FOLLOW_29); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getS_EquationsAccess().getLeftCurlyBracketKeyword_3_0_0());
                      				
                    }
                    loop47:
                    do {
                        int alt47=2;
                        int LA47_0 = input.LA(1);

                        if ( (LA47_0==RULE_ID||LA47_0==36||(LA47_0>=38 && LA47_0<=81)) ) {
                            alt47=1;
                        }


                        switch (alt47) {
                    	case 1 :
                    	    {
                    	    {
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      								newCompositeNode(grammarAccess.getS_EquationsAccess().getEquationsS_EquationParserRuleCall_3_0_1_0_0());
                    	      							
                    	    }
                    	    pushFollow(FOLLOW_22);
                    	    lv_equations_4_0=ruleS_Equation();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      								if (current==null) {
                    	      									current = createModelElementForParent(grammarAccess.getS_EquationsRule());
                    	      								}
                    	      								add(
                    	      									current,
                    	      									"equations",
                    	      									lv_equations_4_0,
                    	      									"gama.core.lang.Gaml.S_Equation");
                    	      								afterParserOrEnumRuleCall();
                    	      							
                    	    }

                    	    }


                    	    }

                    	    otherlv_5=(Token)match(input,21,FOLLOW_29); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_5, grammarAccess.getS_EquationsAccess().getSemicolonKeyword_3_0_1_1());
                    	      					
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop47;
                        }
                    } while (true);

                    otherlv_6=(Token)match(input,33,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_6, grammarAccess.getS_EquationsAccess().getRightCurlyBracketKeyword_3_0_2());
                      				
                    }

                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_7=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getS_EquationsAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 56, ruleS_Equations_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Equation() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Equation_StartIndex = input.index();
        EObject iv_ruleS_Equation = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 57) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_EquationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Equation=ruleS_Equation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Equation; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 57, entryRuleS_Equation_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Equation() throws RecognitionException {
        EObject current = null;
        int ruleS_Equation_StartIndex = input.index();
        Token lv_key_1_0=null;
        EObject lv_expr_0_1 = null;

        EObject lv_expr_0_2 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 58) ) { return current; }
            {
            {
            {
            {
            int alt49=2;
            alt49 = dfa49.predict(input);
            switch (alt49) {
                case 1 :
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_EquationAccess().getExprFunctionParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_30);
                    lv_expr_0_1=ruleFunction();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_EquationRule());
                      						}
                      						set(
                      							current,
                      							"expr",
                      							lv_expr_0_1,
                      							"gama.core.lang.Gaml.Function");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 2 :
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_EquationAccess().getExprVariableRefParserRuleCall_0_0_1());
                      					
                    }
                    pushFollow(FOLLOW_30);
                    lv_expr_0_2=ruleVariableRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_EquationRule());
                      						}
                      						set(
                      							current,
                      							"expr",
                      							lv_expr_0_2,
                      							"gama.core.lang.Gaml.VariableRef");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;

            }


            }


            }
            {
            {
            lv_key_1_0=(Token)match(input,34,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_1_0, grammarAccess.getS_EquationAccess().getKeyEqualsSignKeyword_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_EquationRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_1_0, "=");
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_EquationAccess().getValueExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_EquationRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 58, ruleS_Equation_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Solve() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Solve_StartIndex = input.index();
        EObject iv_ruleS_Solve = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 59) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_SolveRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Solve=ruleS_Solve();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Solve; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 59, entryRuleS_Solve_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Solve() throws RecognitionException {
        EObject current = null;
        int ruleS_Solve_StartIndex = input.index();
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        EObject lv_expr_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 60) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_SolveAccess().getKey_SolveKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_12);
            lv_key_0_0=rule_SolveKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_SolveRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._SolveKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_SolveAccess().getExprEquationRefParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_expr_1_0=ruleEquationRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_SolveRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_1_0,
              						"gama.core.lang.Gaml.EquationRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==RULE_ID||LA50_0==15||(LA50_0>=90 && LA50_0<=102)) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_SolveAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_SolveRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop50;
                }
            } while (true);
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==32) ) {
                alt51=1;
            }
            else if ( (LA51_0==21) ) {
                alt51=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }
            switch (alt51) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_SolveAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_SolveRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_SolveAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 60, ruleS_Solve_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleS_Display() throws RecognitionException {
        EObject current = null;
        int entryRuleS_Display_StartIndex = input.index();
        EObject iv_ruleS_Display = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 61) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_DisplayRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Display=ruleS_Display();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Display; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 61, entryRuleS_Display_StartIndex); }
        }
        return current;
    }
    public final EObject ruleS_Display() throws RecognitionException {
        EObject current = null;
        int ruleS_Display_StartIndex = input.index();
        Token lv_key_0_0=null;
        Token lv_name_1_2=null;
        AntlrDatatypeRuleToken lv_name_1_1 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 62) ) { return current; }
            {
            {
            {
            {
            lv_key_0_0=(Token)match(input,35,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_DisplayAccess().getKeyDisplayKeyword_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_DisplayRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_0_0, "display");
              				
            }

            }


            }
            {
            {
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==RULE_ID||LA52_0==36||(LA52_0>=38 && LA52_0<=81)) ) {
                alt52=1;
            }
            else if ( (LA52_0==RULE_STRING) ) {
                alt52=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_DisplayAccess().getNameValid_IDParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_name_1_1=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_DisplayRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_1_1,
                      							"gama.core.lang.Gaml.Valid_ID");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 2 :
                    {
                    lv_name_1_2=(Token)match(input,RULE_STRING,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_1_2, grammarAccess.getS_DisplayAccess().getNameSTRINGTerminalRuleCall_1_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getS_DisplayRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_1_2,
                      							"gama.core.lang.Gaml.STRING");
                      					
                    }

                    }
                    break;

            }


            }


            }
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==RULE_ID||LA53_0==15||(LA53_0>=90 && LA53_0<=102)) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_DisplayAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_17);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_DisplayRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_DisplayAccess().getBlockD_BlockParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_block_3_0=ruleD_Block();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_DisplayRule());
              					}
              					set(
              						current,
              						"block",
              						lv_block_3_0,
              						"gama.core.lang.Gaml.D_Block");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 62, ruleS_Display_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleD_Block() throws RecognitionException {
        EObject current = null;
        int entryRuleD_Block_StartIndex = input.index();
        EObject iv_ruleD_Block = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 63) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getD_BlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleD_Block=ruleD_Block();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleD_Block; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 63, entryRuleD_Block_StartIndex); }
        }
        return current;
    }
    public final EObject ruleD_Block() throws RecognitionException {
        EObject current = null;
        int ruleD_Block_StartIndex = input.index();
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 64) ) { return current; }
            {
            {
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getD_BlockAccess().getBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,32,FOLLOW_31); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getD_BlockAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( ((LA54_0>=38 && LA54_0<=39)||(LA54_0>=41 && LA54_0<=76)) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getD_BlockAccess().getStatementsD_StatementParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_31);
            	    lv_statements_2_0=ruleD_Statement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getD_BlockRule());
            	      					}
            	      					add(
            	      						current,
            	      						"statements",
            	      						lv_statements_2_0,
            	      						"gama.core.lang.Gaml.D_Statement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop54;
                }
            } while (true);

            otherlv_3=(Token)match(input,33,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getD_BlockAccess().getRightCurlyBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 64, ruleD_Block_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleD_Statement() throws RecognitionException {
        EObject current = null;
        int entryRuleD_Statement_StartIndex = input.index();
        EObject iv_ruleD_Statement = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 65) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getD_StatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleD_Statement=ruleD_Statement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleD_Statement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 65, entryRuleD_Statement_StartIndex); }
        }
        return current;
    }
    public final EObject ruleD_Statement() throws RecognitionException {
        EObject current = null;
        int ruleD_Statement_StartIndex = input.index();
        EObject this_D_Species_0 = null;

        EObject this_S_1Expr_Facets_BlockOrEnd_1 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 66) ) { return current; }
            {
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( ((LA55_0>=38 && LA55_0<=39)) ) {
                alt55=1;
            }
            else if ( ((LA55_0>=41 && LA55_0<=76)) ) {
                alt55=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }
            switch (alt55) {
                case 1 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getD_StatementAccess().getD_SpeciesParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_D_Species_0=ruleD_Species();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_D_Species_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getD_StatementAccess().getS_1Expr_Facets_BlockOrEndParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_1Expr_Facets_BlockOrEnd_1=ruleS_1Expr_Facets_BlockOrEnd();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_1Expr_Facets_BlockOrEnd_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 66, ruleD_Statement_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleD_Species() throws RecognitionException {
        EObject current = null;
        int entryRuleD_Species_StartIndex = input.index();
        EObject iv_ruleD_Species = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 67) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getD_SpeciesRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleD_Species=ruleD_Species();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleD_Species; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 67, entryRuleD_Species_StartIndex); }
        }
        return current;
    }
    public final EObject ruleD_Species() throws RecognitionException {
        EObject current = null;
        int ruleD_Species_StartIndex = input.index();
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        EObject lv_expr_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 68) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getD_SpeciesAccess().getKey_SpeciesKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_5);
            lv_key_0_0=rule_SpeciesKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getD_SpeciesRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._SpeciesKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getD_SpeciesAccess().getExprExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_expr_1_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getD_SpeciesRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_1_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==RULE_ID||LA56_0==15||(LA56_0>=90 && LA56_0<=102)) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getD_SpeciesAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getD_SpeciesRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==32) ) {
                alt57=1;
            }
            else if ( (LA57_0==21) ) {
                alt57=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }
            switch (alt57) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getD_SpeciesAccess().getBlockD_BlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleD_Block();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getD_SpeciesRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.D_Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getD_SpeciesAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 68, ruleD_Species_StartIndex); }
        }
        return current;
    }
    public final String entryRule_EquationsKey() throws RecognitionException {
        String current = null;
        int entryRule_EquationsKey_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_rule_EquationsKey = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 69) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_EquationsKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_EquationsKey=rule_EquationsKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_EquationsKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 69, entryRule_EquationsKey_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken rule_EquationsKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int rule_EquationsKey_StartIndex = input.index();
        Token kw=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 70) ) { return current; }
            {
            kw=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(kw);
              		newLeafNode(kw, grammarAccess.get_EquationsKeyAccess().getEquationKeyword());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 70, rule_EquationsKey_StartIndex); }
        }
        return current;
    }
    public final String entryRule_SolveKey() throws RecognitionException {
        String current = null;
        int entryRule_SolveKey_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_rule_SolveKey = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 71) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_SolveKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_SolveKey=rule_SolveKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_SolveKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 71, entryRule_SolveKey_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken rule_SolveKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int rule_SolveKey_StartIndex = input.index();
        Token kw=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 72) ) { return current; }
            {
            kw=(Token)match(input,37,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(kw);
              		newLeafNode(kw, grammarAccess.get_SolveKeyAccess().getSolveKeyword());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 72, rule_SolveKey_StartIndex); }
        }
        return current;
    }
    public final String entryRule_SpeciesKey() throws RecognitionException {
        String current = null;
        int entryRule_SpeciesKey_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_rule_SpeciesKey = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 73) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_SpeciesKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_SpeciesKey=rule_SpeciesKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_SpeciesKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 73, entryRule_SpeciesKey_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken rule_SpeciesKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int rule_SpeciesKey_StartIndex = input.index();
        Token kw=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 74) ) { return current; }
            {
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==38) ) {
                alt58=1;
            }
            else if ( (LA58_0==39) ) {
                alt58=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }
            switch (alt58) {
                case 1 :
                    {
                    kw=(Token)match(input,38,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_SpeciesKeyAccess().getSpeciesKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    kw=(Token)match(input,39,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_SpeciesKeyAccess().getGridKeyword_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 74, rule_SpeciesKey_StartIndex); }
        }
        return current;
    }
    public final String entryRule_ExperimentKey() throws RecognitionException {
        String current = null;
        int entryRule_ExperimentKey_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_rule_ExperimentKey = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 75) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_ExperimentKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_ExperimentKey=rule_ExperimentKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_ExperimentKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 75, entryRule_ExperimentKey_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken rule_ExperimentKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int rule_ExperimentKey_StartIndex = input.index();
        Token kw=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 76) ) { return current; }
            {
            kw=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(kw);
              		newLeafNode(kw, grammarAccess.get_ExperimentKeyAccess().getExperimentKeyword());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 76, rule_ExperimentKey_StartIndex); }
        }
        return current;
    }
    public final String entryRule_1Expr_Facets_BlockOrEnd_Key() throws RecognitionException {
        String current = null;
        int entryRule_1Expr_Facets_BlockOrEnd_Key_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_rule_1Expr_Facets_BlockOrEnd_Key = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 77) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_1Expr_Facets_BlockOrEnd_Key=rule_1Expr_Facets_BlockOrEnd_Key();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_1Expr_Facets_BlockOrEnd_Key.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 77, entryRule_1Expr_Facets_BlockOrEnd_Key_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken rule_1Expr_Facets_BlockOrEnd_Key() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int rule_1Expr_Facets_BlockOrEnd_Key_StartIndex = input.index();
        Token kw=null;
        AntlrDatatypeRuleToken this__LayerKey_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 78) ) { return current; }
            {
            int alt59=27;
            switch ( input.LA(1) ) {
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
                {
                alt59=1;
                }
                break;
            case 41:
                {
                alt59=2;
                }
                break;
            case 42:
                {
                alt59=3;
                }
                break;
            case 43:
                {
                alt59=4;
                }
                break;
            case 44:
                {
                alt59=5;
                }
                break;
            case 45:
                {
                alt59=6;
                }
                break;
            case 46:
                {
                alt59=7;
                }
                break;
            case 47:
                {
                alt59=8;
                }
                break;
            case 48:
                {
                alt59=9;
                }
                break;
            case 49:
                {
                alt59=10;
                }
                break;
            case 50:
                {
                alt59=11;
                }
                break;
            case 51:
                {
                alt59=12;
                }
                break;
            case 52:
                {
                alt59=13;
                }
                break;
            case 53:
                {
                alt59=14;
                }
                break;
            case 54:
                {
                alt59=15;
                }
                break;
            case 55:
                {
                alt59=16;
                }
                break;
            case 56:
                {
                alt59=17;
                }
                break;
            case 57:
                {
                alt59=18;
                }
                break;
            case 58:
                {
                alt59=19;
                }
                break;
            case 59:
                {
                alt59=20;
                }
                break;
            case 60:
                {
                alt59=21;
                }
                break;
            case 61:
                {
                alt59=22;
                }
                break;
            case 62:
                {
                alt59=23;
                }
                break;
            case 63:
                {
                alt59=24;
                }
                break;
            case 64:
                {
                alt59=25;
                }
                break;
            case 65:
                {
                alt59=26;
                }
                break;
            case 66:
                {
                alt59=27;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }

            switch (alt59) {
                case 1 :
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().get_LayerKeyParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__LayerKey_0=rule_LayerKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__LayerKey_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    kw=(Token)match(input,41,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getAskKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    {
                    kw=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getReleaseKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    {
                    kw=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getCaptureKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    {
                    kw=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getCreateKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    {
                    kw=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getWriteKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    {
                    kw=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getErrorKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    {
                    kw=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getWarnKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    {
                    kw=(Token)match(input,48,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getExceptionKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    {
                    kw=(Token)match(input,49,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getSaveKeyword_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    {
                    kw=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getAssertKeyword_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    {
                    kw=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getInspectKeyword_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    {
                    kw=(Token)match(input,52,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getBrowseKeyword_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    {
                    kw=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getDrawKeyword_13());
                      		
                    }

                    }
                    break;
                case 15 :
                    {
                    kw=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getUsingKeyword_14());
                      		
                    }

                    }
                    break;
                case 16 :
                    {
                    kw=(Token)match(input,55,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getSwitchKeyword_15());
                      		
                    }

                    }
                    break;
                case 17 :
                    {
                    kw=(Token)match(input,56,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getPutKeyword_16());
                      		
                    }

                    }
                    break;
                case 18 :
                    {
                    kw=(Token)match(input,57,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getAddKeyword_17());
                      		
                    }

                    }
                    break;
                case 19 :
                    {
                    kw=(Token)match(input,58,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getRemoveKeyword_18());
                      		
                    }

                    }
                    break;
                case 20 :
                    {
                    kw=(Token)match(input,59,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getMatchKeyword_19());
                      		
                    }

                    }
                    break;
                case 21 :
                    {
                    kw=(Token)match(input,60,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getMatch_betweenKeyword_20());
                      		
                    }

                    }
                    break;
                case 22 :
                    {
                    kw=(Token)match(input,61,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getMatch_oneKeyword_21());
                      		
                    }

                    }
                    break;
                case 23 :
                    {
                    kw=(Token)match(input,62,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getParameterKeyword_22());
                      		
                    }

                    }
                    break;
                case 24 :
                    {
                    kw=(Token)match(input,63,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getStatusKeyword_23());
                      		
                    }

                    }
                    break;
                case 25 :
                    {
                    kw=(Token)match(input,64,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getHighlightKeyword_24());
                      		
                    }

                    }
                    break;
                case 26 :
                    {
                    kw=(Token)match(input,65,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getFocus_onKeyword_25());
                      		
                    }

                    }
                    break;
                case 27 :
                    {
                    kw=(Token)match(input,66,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getLayoutKeyword_26());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 78, rule_1Expr_Facets_BlockOrEnd_Key_StartIndex); }
        }
        return current;
    }
    public final String entryRule_LayerKey() throws RecognitionException {
        String current = null;
        int entryRule_LayerKey_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_rule_LayerKey = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 79) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_LayerKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_LayerKey=rule_LayerKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_LayerKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 79, entryRule_LayerKey_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken rule_LayerKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int rule_LayerKey_StartIndex = input.index();
        Token kw=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 80) ) { return current; }
            {
            int alt60=10;
            switch ( input.LA(1) ) {
            case 67:
                {
                alt60=1;
                }
                break;
            case 68:
                {
                alt60=2;
                }
                break;
            case 69:
                {
                alt60=3;
                }
                break;
            case 70:
                {
                alt60=4;
                }
                break;
            case 71:
                {
                alt60=5;
                }
                break;
            case 72:
                {
                alt60=6;
                }
                break;
            case 73:
                {
                alt60=7;
                }
                break;
            case 74:
                {
                alt60=8;
                }
                break;
            case 75:
                {
                alt60=9;
                }
                break;
            case 76:
                {
                alt60=10;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }

            switch (alt60) {
                case 1 :
                    {
                    kw=(Token)match(input,67,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getLightKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    kw=(Token)match(input,68,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getCameraKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    {
                    kw=(Token)match(input,69,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getImageKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    {
                    kw=(Token)match(input,70,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getDataKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    {
                    kw=(Token)match(input,71,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getChartKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    {
                    kw=(Token)match(input,72,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getAgentsKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    {
                    kw=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getGraphicsKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    {
                    kw=(Token)match(input,74,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getEventKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    {
                    kw=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getOverlayKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    {
                    kw=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getDatalistKeyword_9());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 80, rule_LayerKey_StartIndex); }
        }
        return current;
    }
    public final String entryRule_DoKey() throws RecognitionException {
        String current = null;
        int entryRule_DoKey_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_rule_DoKey = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 81) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_DoKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_DoKey=rule_DoKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_DoKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 81, entryRule_DoKey_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken rule_DoKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int rule_DoKey_StartIndex = input.index();
        Token kw=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 82) ) { return current; }
            {
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==77) ) {
                alt61=1;
            }
            else if ( (LA61_0==78) ) {
                alt61=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }
            switch (alt61) {
                case 1 :
                    {
                    kw=(Token)match(input,77,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_DoKeyAccess().getDoKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    kw=(Token)match(input,78,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_DoKeyAccess().getInvokeKeyword_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 82, rule_DoKey_StartIndex); }
        }
        return current;
    }
    public final String entryRule_ReflexKey() throws RecognitionException {
        String current = null;
        int entryRule_ReflexKey_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_rule_ReflexKey = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 83) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_ReflexKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_ReflexKey=rule_ReflexKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_ReflexKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 83, entryRule_ReflexKey_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken rule_ReflexKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int rule_ReflexKey_StartIndex = input.index();
        Token kw=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 84) ) { return current; }
            {
            int alt62=3;
            switch ( input.LA(1) ) {
            case 79:
                {
                alt62=1;
                }
                break;
            case 80:
                {
                alt62=2;
                }
                break;
            case 81:
                {
                alt62=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }

            switch (alt62) {
                case 1 :
                    {
                    kw=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_ReflexKeyAccess().getInitKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    kw=(Token)match(input,80,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_ReflexKeyAccess().getReflexKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    {
                    kw=(Token)match(input,81,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_ReflexKeyAccess().getAspectKeyword_2());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 84, rule_ReflexKey_StartIndex); }
        }
        return current;
    }
    public final String entryRule_AssignmentKey() throws RecognitionException {
        String current = null;
        int entryRule_AssignmentKey_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_rule_AssignmentKey = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 85) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_AssignmentKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_AssignmentKey=rule_AssignmentKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_AssignmentKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 85, entryRule_AssignmentKey_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken rule_AssignmentKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int rule_AssignmentKey_StartIndex = input.index();
        Token kw=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 86) ) { return current; }
            {
            int alt63=8;
            alt63 = dfa63.predict(input);
            switch (alt63) {
                case 1 :
                    {
                    kw=(Token)match(input,15,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getLessThanSignHyphenMinusKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    kw=(Token)match(input,82,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getLessThanSignLessThanSignKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    {
                    {
                    kw=(Token)match(input,83,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getGreaterThanSignKeyword_2_0());
                      			
                    }
                    kw=(Token)match(input,83,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getGreaterThanSignKeyword_2_1());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    {
                    kw=(Token)match(input,84,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getLessThanSignLessThanSignPlusSignKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    {
                    {
                    kw=(Token)match(input,83,FOLLOW_33); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getGreaterThanSignKeyword_4_0());
                      			
                    }
                    kw=(Token)match(input,85,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getGreaterThanSignHyphenMinusKeyword_4_1());
                      			
                    }

                    }


                    }
                    break;
                case 6 :
                    {
                    kw=(Token)match(input,86,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getPlusSignLessThanSignHyphenMinusKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    {
                    kw=(Token)match(input,87,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getLessThanSignPlusSignKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    {
                    kw=(Token)match(input,85,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getGreaterThanSignHyphenMinusKeyword_7());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 86, rule_AssignmentKey_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleActionArguments() throws RecognitionException {
        EObject current = null;
        int entryRuleActionArguments_StartIndex = input.index();
        EObject iv_ruleActionArguments = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 87) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActionArgumentsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleActionArguments=ruleActionArguments();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActionArguments; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 87, entryRuleActionArguments_StartIndex); }
        }
        return current;
    }
    public final EObject ruleActionArguments() throws RecognitionException {
        EObject current = null;
        int ruleActionArguments_StartIndex = input.index();
        Token otherlv_1=null;
        EObject lv_args_0_0 = null;

        EObject lv_args_2_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 88) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getActionArgumentsAccess().getArgsArgumentDefinitionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_34);
            lv_args_0_0=ruleArgumentDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getActionArgumentsRule());
              					}
              					add(
              						current,
              						"args",
              						lv_args_0_0,
              						"gama.core.lang.Gaml.ArgumentDefinition");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==88) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    {
            	    otherlv_1=(Token)match(input,88,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getActionArgumentsAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getActionArgumentsAccess().getArgsArgumentDefinitionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_34);
            	    lv_args_2_0=ruleArgumentDefinition();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getActionArgumentsRule());
            	      						}
            	      						add(
            	      							current,
            	      							"args",
            	      							lv_args_2_0,
            	      							"gama.core.lang.Gaml.ArgumentDefinition");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop64;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 88, ruleActionArguments_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleArgumentDefinition() throws RecognitionException {
        EObject current = null;
        int entryRuleArgumentDefinition_StartIndex = input.index();
        EObject iv_ruleArgumentDefinition = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 89) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArgumentDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleArgumentDefinition=ruleArgumentDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArgumentDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 89, entryRuleArgumentDefinition_StartIndex); }
        }
        return current;
    }
    public final EObject ruleArgumentDefinition() throws RecognitionException {
        EObject current = null;
        int ruleArgumentDefinition_StartIndex = input.index();
        Token otherlv_2=null;
        EObject lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_default_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 90) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArgumentDefinitionAccess().getTypeTypeRefParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_12);
            lv_type_0_0=ruleTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getArgumentDefinitionRule());
              					}
              					set(
              						current,
              						"type",
              						lv_type_0_0,
              						"gama.core.lang.Gaml.TypeRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArgumentDefinitionAccess().getNameValid_IDParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_35);
            lv_name_1_0=ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getArgumentDefinitionRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.Valid_ID");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==15) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    {
                    otherlv_2=(Token)match(input,15,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getArgumentDefinitionAccess().getLessThanSignHyphenMinusKeyword_2_0());
                      			
                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getArgumentDefinitionAccess().getDefaultExpressionParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_default_3_0=ruleExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getArgumentDefinitionRule());
                      						}
                      						set(
                      							current,
                      							"default",
                      							lv_default_3_0,
                      							"gama.core.lang.Gaml.Expression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 90, ruleArgumentDefinition_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleFacet() throws RecognitionException {
        EObject current = null;
        int entryRuleFacet_StartIndex = input.index();
        EObject iv_ruleFacet = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 91) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFacetRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFacet=ruleFacet();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFacet; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 91, entryRuleFacet_StartIndex); }
        }
        return current;
    }
    public final EObject ruleFacet() throws RecognitionException {
        EObject current = null;
        int ruleFacet_StartIndex = input.index();
        EObject this_ActionFacet_0 = null;

        EObject this_DefinitionFacet_1 = null;

        EObject this_ClassicFacet_2 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 92) ) { return current; }
            {
            int alt66=3;
            switch ( input.LA(1) ) {
            case 100:
            case 101:
                {
                alt66=1;
                }
                break;
            case 90:
                {
                alt66=2;
                }
                break;
            case RULE_ID:
            case 15:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 102:
                {
                alt66=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 66, 0, input);

                throw nvae;
            }

            switch (alt66) {
                case 1 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getFacetAccess().getActionFacetParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ActionFacet_0=ruleActionFacet();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ActionFacet_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getFacetAccess().getDefinitionFacetParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_DefinitionFacet_1=ruleDefinitionFacet();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_DefinitionFacet_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getFacetAccess().getClassicFacetParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ClassicFacet_2=ruleClassicFacet();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ClassicFacet_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 92, ruleFacet_StartIndex); }
        }
        return current;
    }
    public final String entryRuleClassicFacetKey() throws RecognitionException {
        String current = null;
        int entryRuleClassicFacetKey_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_ruleClassicFacetKey = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 93) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getClassicFacetKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleClassicFacetKey=ruleClassicFacetKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleClassicFacetKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 93, entryRuleClassicFacetKey_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken ruleClassicFacetKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int ruleClassicFacetKey_StartIndex = input.index();
        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 94) ) { return current; }
            {
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_36); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_ID_0, grammarAccess.getClassicFacetKeyAccess().getIDTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,89,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getClassicFacetKeyAccess().getColonKeyword_1());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 94, ruleClassicFacetKey_StartIndex); }
        }
        return current;
    }
    public final String entryRuleDefinitionFacetKey() throws RecognitionException {
        String current = null;
        int entryRuleDefinitionFacetKey_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_ruleDefinitionFacetKey = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 95) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDefinitionFacetKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDefinitionFacetKey=ruleDefinitionFacetKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDefinitionFacetKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 95, entryRuleDefinitionFacetKey_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken ruleDefinitionFacetKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int ruleDefinitionFacetKey_StartIndex = input.index();
        Token kw=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 96) ) { return current; }
            {
            kw=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(kw);
              		newLeafNode(kw, grammarAccess.getDefinitionFacetKeyAccess().getReturnsKeyword());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 96, ruleDefinitionFacetKey_StartIndex); }
        }
        return current;
    }
    public final String entryRuleSpecialFacetKey() throws RecognitionException {
        String current = null;
        int entryRuleSpecialFacetKey_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_ruleSpecialFacetKey = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 97) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSpecialFacetKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSpecialFacetKey=ruleSpecialFacetKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSpecialFacetKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 97, entryRuleSpecialFacetKey_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken ruleSpecialFacetKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int ruleSpecialFacetKey_StartIndex = input.index();
        Token kw=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 98) ) { return current; }
            {
            int alt67=9;
            switch ( input.LA(1) ) {
            case 91:
                {
                alt67=1;
                }
                break;
            case 92:
                {
                alt67=2;
                }
                break;
            case 93:
                {
                alt67=3;
                }
                break;
            case 94:
                {
                alt67=4;
                }
                break;
            case 95:
                {
                alt67=5;
                }
                break;
            case 96:
                {
                alt67=6;
                }
                break;
            case 97:
                {
                alt67=7;
                }
                break;
            case 98:
                {
                alt67=8;
                }
                break;
            case 99:
                {
                alt67=9;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }

            switch (alt67) {
                case 1 :
                    {
                    kw=(Token)match(input,91,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getDataKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    kw=(Token)match(input,92,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getInitKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    {
                    kw=(Token)match(input,93,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getLayoutKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    {
                    kw=(Token)match(input,94,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getImageKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    {
                    kw=(Token)match(input,95,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getParameterKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    {
                    kw=(Token)match(input,96,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getAspectKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    {
                    kw=(Token)match(input,97,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getLightKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    {
                    kw=(Token)match(input,98,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getAsKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    {
                    kw=(Token)match(input,99,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getSpeciesKeyword_8());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 98, ruleSpecialFacetKey_StartIndex); }
        }
        return current;
    }
    public final String entryRuleActionFacetKey() throws RecognitionException {
        String current = null;
        int entryRuleActionFacetKey_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_ruleActionFacetKey = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 99) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActionFacetKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleActionFacetKey=ruleActionFacetKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActionFacetKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 99, entryRuleActionFacetKey_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken ruleActionFacetKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int ruleActionFacetKey_StartIndex = input.index();
        Token kw=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 100) ) { return current; }
            {
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==100) ) {
                alt68=1;
            }
            else if ( (LA68_0==101) ) {
                alt68=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;
            }
            switch (alt68) {
                case 1 :
                    {
                    kw=(Token)match(input,100,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getActionFacetKeyAccess().getActionKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    kw=(Token)match(input,101,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getActionFacetKeyAccess().getOn_changeKeyword_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 100, ruleActionFacetKey_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleClassicFacet() throws RecognitionException {
        EObject current = null;
        int entryRuleClassicFacet_StartIndex = input.index();
        EObject iv_ruleClassicFacet = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 101) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getClassicFacetRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleClassicFacet=ruleClassicFacet();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleClassicFacet; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 101, entryRuleClassicFacet_StartIndex); }
        }
        return current;
    }
    public final EObject ruleClassicFacet() throws RecognitionException {
        EObject current = null;
        int ruleClassicFacet_StartIndex = input.index();
        Token lv_key_1_0=null;
        Token lv_key_2_0=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        AntlrDatatypeRuleToken lv_key_3_0 = null;

        EObject lv_expr_4_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 102) ) { return current; }
            {
            {
            int alt69=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt69=1;
                }
                break;
            case 15:
                {
                alt69=2;
                }
                break;
            case 102:
                {
                alt69=3;
                }
                break;
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
                {
                alt69=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }

            switch (alt69) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassicFacetAccess().getKeyClassicFacetKeyParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_5);
                    lv_key_0_0=ruleClassicFacetKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassicFacetRule());
                      						}
                      						set(
                      							current,
                      							"key",
                      							lv_key_0_0,
                      							"gama.core.lang.Gaml.ClassicFacetKey");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    {
                    {
                    lv_key_1_0=(Token)match(input,15,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_key_1_0, grammarAccess.getClassicFacetAccess().getKeyLessThanSignHyphenMinusKeyword_0_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getClassicFacetRule());
                      						}
                      						setWithLastConsumed(current, "key", lv_key_1_0, "<-");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    {
                    {
                    {
                    lv_key_2_0=(Token)match(input,102,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_key_2_0, grammarAccess.getClassicFacetAccess().getKeyHyphenMinusGreaterThanSignKeyword_0_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getClassicFacetRule());
                      						}
                      						setWithLastConsumed(current, "key", lv_key_2_0, "->");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassicFacetAccess().getKeySpecialFacetKeyParserRuleCall_0_3_0());
                      					
                    }
                    pushFollow(FOLLOW_5);
                    lv_key_3_0=ruleSpecialFacetKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassicFacetRule());
                      						}
                      						set(
                      							current,
                      							"key",
                      							lv_key_3_0,
                      							"gama.core.lang.Gaml.SpecialFacetKey");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getClassicFacetAccess().getExprExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_expr_4_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getClassicFacetRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_4_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 102, ruleClassicFacet_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleDefinitionFacet() throws RecognitionException {
        EObject current = null;
        int entryRuleDefinitionFacet_StartIndex = input.index();
        EObject iv_ruleDefinitionFacet = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 103) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDefinitionFacetRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDefinitionFacet=ruleDefinitionFacet();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDefinitionFacet; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 103, entryRuleDefinitionFacet_StartIndex); }
        }
        return current;
    }
    public final EObject ruleDefinitionFacet() throws RecognitionException {
        EObject current = null;
        int ruleDefinitionFacet_StartIndex = input.index();
        Token lv_name_1_2=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_1 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 104) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDefinitionFacetAccess().getKeyDefinitionFacetKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_13);
            lv_key_0_0=ruleDefinitionFacetKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDefinitionFacetRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml.DefinitionFacetKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            {
            {
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==RULE_ID||LA70_0==36||(LA70_0>=38 && LA70_0<=81)) ) {
                alt70=1;
            }
            else if ( (LA70_0==RULE_STRING) ) {
                alt70=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }
            switch (alt70) {
                case 1 :
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getDefinitionFacetAccess().getNameValid_IDParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_name_1_1=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getDefinitionFacetRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_1_1,
                      							"gama.core.lang.Gaml.Valid_ID");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 2 :
                    {
                    lv_name_1_2=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_1_2, grammarAccess.getDefinitionFacetAccess().getNameSTRINGTerminalRuleCall_1_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDefinitionFacetRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_1_2,
                      							"gama.core.lang.Gaml.STRING");
                      					
                    }

                    }
                    break;

            }


            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 104, ruleDefinitionFacet_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleActionFacet() throws RecognitionException {
        EObject current = null;
        int entryRuleActionFacet_StartIndex = input.index();
        EObject iv_ruleActionFacet = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 105) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActionFacetRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleActionFacet=ruleActionFacet();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActionFacet; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 105, entryRuleActionFacet_StartIndex); }
        }
        return current;
    }
    public final EObject ruleActionFacet() throws RecognitionException {
        EObject current = null;
        int ruleActionFacet_StartIndex = input.index();
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        EObject lv_expr_1_0 = null;

        EObject lv_block_2_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 106) ) { return current; }
            {
            {
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getActionFacetAccess().getKeyActionFacetKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_37);
            lv_key_0_0=ruleActionFacetKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getActionFacetRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml.ActionFacetKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==RULE_ID||LA71_0==36||(LA71_0>=38 && LA71_0<=81)) ) {
                alt71=1;
            }
            else if ( (LA71_0==32) ) {
                alt71=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 71, 0, input);

                throw nvae;
            }
            switch (alt71) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionFacetAccess().getExprActionRefParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_expr_1_0=ruleActionRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionFacetRule());
                      						}
                      						set(
                      							current,
                      							"expr",
                      							lv_expr_1_0,
                      							"gama.core.lang.Gaml.ActionRef");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionFacetAccess().getBlockBlockParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_2_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionFacetRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_2_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 106, ruleActionFacet_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleBlock() throws RecognitionException {
        EObject current = null;
        int entryRuleBlock_StartIndex = input.index();
        EObject iv_ruleBlock = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 107) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBlock=ruleBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBlock; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 107, entryRuleBlock_StartIndex); }
        }
        return current;
    }
    public final EObject ruleBlock() throws RecognitionException {
        EObject current = null;
        int ruleBlock_StartIndex = input.index();
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 108) ) { return current; }
            {
            {
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getBlockAccess().getBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,32,FOLLOW_38); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getBlockAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            {
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( ((LA72_0>=RULE_ID && LA72_0<=RULE_KEYWORD)||(LA72_0>=23 && LA72_0<=24)||LA72_0==26||(LA72_0>=28 && LA72_0<=29)||(LA72_0>=31 && LA72_0<=32)||(LA72_0>=35 && LA72_0<=81)||LA72_0==112||(LA72_0>=116 && LA72_0<=119)) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getBlockAccess().getStatementsStatementParserRuleCall_2_0_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_38);
            	    lv_statements_2_0=ruleStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getBlockRule());
            	      						}
            	      						add(
            	      							current,
            	      							"statements",
            	      							lv_statements_2_0,
            	      							"gama.core.lang.Gaml.Statement");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop72;
                }
            } while (true);

            otherlv_3=(Token)match(input,33,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_3, grammarAccess.getBlockAccess().getRightCurlyBracketKeyword_2_1());
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 108, ruleBlock_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;
        int entryRuleExpression_StartIndex = input.index();
        EObject iv_ruleExpression = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 109) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExpression=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 109, entryRuleExpression_StartIndex); }
        }
        return current;
    }
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;
        int ruleExpression_StartIndex = input.index();
        EObject this_Pair_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 110) ) { return current; }
            {
            if ( state.backtracking==0 ) {

              		/* */
              	
            }
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getExpressionAccess().getPairParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_Pair_0=rulePair();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current = this_Pair_0;
              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 110, ruleExpression_StartIndex); }
        }
        return current;
    }
    public final EObject entryRulePair() throws RecognitionException {
        EObject current = null;
        int entryRulePair_StartIndex = input.index();
        EObject iv_rulePair = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 111) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPairRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePair=rulePair();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePair; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 111, entryRulePair_StartIndex); }
        }
        return current;
    }
    public final EObject rulePair() throws RecognitionException {
        EObject current = null;
        int rulePair_StartIndex = input.index();
        Token lv_op_2_0=null;
        EObject this_If_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 112) ) { return current; }
            {
            {
            if ( state.backtracking==0 ) {

              			/* */
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getPairAccess().getIfParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_39);
            this_If_0=ruleIf();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_If_0;
              			afterParserOrEnumRuleCall();
              		
            }
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==103) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					/* */
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElementAndSet(
                      						grammarAccess.getPairAccess().getBinaryOperatorLeftAction_1_0(),
                      						current);
                      				
                    }

                    }
                    {
                    {
                    lv_op_2_0=(Token)match(input,103,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_2_0, grammarAccess.getPairAccess().getOpColonColonKeyword_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getPairRule());
                      						}
                      						setWithLastConsumed(current, "op", lv_op_2_0, "::");
                      					
                    }

                    }


                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPairAccess().getRightIfParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_right_3_0=ruleIf();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPairRule());
                      						}
                      						set(
                      							current,
                      							"right",
                      							lv_right_3_0,
                      							"gama.core.lang.Gaml.If");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 112, rulePair_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleIf() throws RecognitionException {
        EObject current = null;
        int entryRuleIf_StartIndex = input.index();
        EObject iv_ruleIf = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 113) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIfRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleIf=ruleIf();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIf; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 113, entryRuleIf_StartIndex); }
        }
        return current;
    }
    public final EObject ruleIf() throws RecognitionException {
        EObject current = null;
        int ruleIf_StartIndex = input.index();
        Token lv_op_2_0=null;
        Token otherlv_4=null;
        EObject this_Or_0 = null;

        EObject lv_right_3_0 = null;

        EObject lv_ifFalse_5_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 114) ) { return current; }
            {
            {
            if ( state.backtracking==0 ) {

              			/* */
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getIfAccess().getOrParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_40);
            this_Or_0=ruleOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Or_0;
              			afterParserOrEnumRuleCall();
              		
            }
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==104) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					/* */
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElementAndSet(
                      						grammarAccess.getIfAccess().getIfLeftAction_1_0(),
                      						current);
                      				
                    }

                    }
                    {
                    {
                    lv_op_2_0=(Token)match(input,104,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_2_0, grammarAccess.getIfAccess().getOpQuestionMarkKeyword_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getIfRule());
                      						}
                      						setWithLastConsumed(current, "op", lv_op_2_0, "?");
                      					
                    }

                    }


                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getIfAccess().getRightOrParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_36);
                    lv_right_3_0=ruleOr();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getIfRule());
                      						}
                      						set(
                      							current,
                      							"right",
                      							lv_right_3_0,
                      							"gama.core.lang.Gaml.Or");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }
                    {
                    otherlv_4=(Token)match(input,89,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_4, grammarAccess.getIfAccess().getColonKeyword_1_3_0());
                      				
                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getIfAccess().getIfFalseOrParserRuleCall_1_3_1_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    lv_ifFalse_5_0=ruleOr();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getIfRule());
                      							}
                      							set(
                      								current,
                      								"ifFalse",
                      								lv_ifFalse_5_0,
                      								"gama.core.lang.Gaml.Or");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 114, ruleIf_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleOr() throws RecognitionException {
        EObject current = null;
        int entryRuleOr_StartIndex = input.index();
        EObject iv_ruleOr = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 115) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOrRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOr=ruleOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOr; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 115, entryRuleOr_StartIndex); }
        }
        return current;
    }
    public final EObject ruleOr() throws RecognitionException {
        EObject current = null;
        int ruleOr_StartIndex = input.index();
        Token lv_op_2_0=null;
        EObject this_And_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 116) ) { return current; }
            {
            {
            if ( state.backtracking==0 ) {

              			/* */
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getOrAccess().getAndParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_41);
            this_And_0=ruleAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_And_0;
              			afterParserOrEnumRuleCall();
              		
            }
            loop75:
            do {
                int alt75=2;
                int LA75_0 = input.LA(1);

                if ( (LA75_0==105) ) {
                    alt75=1;
                }


                switch (alt75) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					/* */
            	      				
            	    }
            	    if ( state.backtracking==0 ) {

            	      					current = forceCreateModelElementAndSet(
            	      						grammarAccess.getOrAccess().getBinaryOperatorLeftAction_1_0(),
            	      						current);
            	      				
            	    }

            	    }
            	    {
            	    {
            	    lv_op_2_0=(Token)match(input,105,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						newLeafNode(lv_op_2_0, grammarAccess.getOrAccess().getOpOrKeyword_1_1_0());
            	      					
            	    }
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElement(grammarAccess.getOrRule());
            	      						}
            	      						setWithLastConsumed(current, "op", lv_op_2_0, "or");
            	      					
            	    }

            	    }


            	    }
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_41);
            	    lv_right_3_0=ruleAnd();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getOrRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"gama.core.lang.Gaml.And");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop75;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 116, ruleOr_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleAnd() throws RecognitionException {
        EObject current = null;
        int entryRuleAnd_StartIndex = input.index();
        EObject iv_ruleAnd = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 117) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAndRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAnd=ruleAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAnd; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 117, entryRuleAnd_StartIndex); }
        }
        return current;
    }
    public final EObject ruleAnd() throws RecognitionException {
        EObject current = null;
        int ruleAnd_StartIndex = input.index();
        Token lv_op_2_0=null;
        EObject this_Cast_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 118) ) { return current; }
            {
            {
            if ( state.backtracking==0 ) {

              			/* */
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getAndAccess().getCastParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_42);
            this_Cast_0=ruleCast();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Cast_0;
              			afterParserOrEnumRuleCall();
              		
            }
            loop76:
            do {
                int alt76=2;
                int LA76_0 = input.LA(1);

                if ( (LA76_0==106) ) {
                    alt76=1;
                }


                switch (alt76) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					/* */
            	      				
            	    }
            	    if ( state.backtracking==0 ) {

            	      					current = forceCreateModelElementAndSet(
            	      						grammarAccess.getAndAccess().getBinaryOperatorLeftAction_1_0(),
            	      						current);
            	      				
            	    }

            	    }
            	    {
            	    {
            	    lv_op_2_0=(Token)match(input,106,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						newLeafNode(lv_op_2_0, grammarAccess.getAndAccess().getOpAndKeyword_1_1_0());
            	      					
            	    }
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElement(grammarAccess.getAndRule());
            	      						}
            	      						setWithLastConsumed(current, "op", lv_op_2_0, "and");
            	      					
            	    }

            	    }


            	    }
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getAndAccess().getRightCastParserRuleCall_1_2_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_42);
            	    lv_right_3_0=ruleCast();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getAndRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"gama.core.lang.Gaml.Cast");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop76;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 118, ruleAnd_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleCast() throws RecognitionException {
        EObject current = null;
        int entryRuleCast_StartIndex = input.index();
        EObject iv_ruleCast = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 119) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCastRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleCast=ruleCast();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCast; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 119, entryRuleCast_StartIndex); }
        }
        return current;
    }
    public final EObject ruleCast() throws RecognitionException {
        EObject current = null;
        int ruleCast_StartIndex = input.index();
        Token lv_op_2_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject this_Comparison_0 = null;

        EObject lv_right_3_0 = null;

        EObject lv_right_5_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 120) ) { return current; }
            {
            {
            if ( state.backtracking==0 ) {

              			/* */
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getCastAccess().getComparisonParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_11);
            this_Comparison_0=ruleComparison();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Comparison_0;
              			afterParserOrEnumRuleCall();
              		
            }
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==18) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						/* */
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						current = forceCreateModelElementAndSet(
                      							grammarAccess.getCastAccess().getBinaryOperatorLeftAction_1_0_0(),
                      							current);
                      					
                    }

                    }
                    {
                    {
                    lv_op_2_0=(Token)match(input,18,FOLLOW_43); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_op_2_0, grammarAccess.getCastAccess().getOpAsKeyword_1_0_1_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getCastRule());
                      							}
                      							setWithLastConsumed(current, "op", lv_op_2_0, "as");
                      						
                    }

                    }


                    }


                    }
                    int alt77=2;
                    int LA77_0 = input.LA(1);

                    if ( (LA77_0==RULE_ID||LA77_0==38) ) {
                        alt77=1;
                    }
                    else if ( (LA77_0==29) ) {
                        alt77=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 77, 0, input);

                        throw nvae;
                    }
                    switch (alt77) {
                        case 1 :
                            {
                            {
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getCastAccess().getRightTypeRefParserRuleCall_1_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_2);
                            lv_right_3_0=ruleTypeRef();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getCastRule());
                              							}
                              							set(
                              								current,
                              								"right",
                              								lv_right_3_0,
                              								"gama.core.lang.Gaml.TypeRef");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            {
                            {
                            otherlv_4=(Token)match(input,29,FOLLOW_25); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_4, grammarAccess.getCastAccess().getLeftParenthesisKeyword_1_1_1_0());
                              					
                            }
                            {
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getCastAccess().getRightTypeRefParserRuleCall_1_1_1_1_0());
                              							
                            }
                            pushFollow(FOLLOW_26);
                            lv_right_5_0=ruleTypeRef();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getCastRule());
                              								}
                              								set(
                              									current,
                              									"right",
                              									lv_right_5_0,
                              									"gama.core.lang.Gaml.TypeRef");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            otherlv_6=(Token)match(input,30,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getCastAccess().getRightParenthesisKeyword_1_1_1_2());
                              					
                            }

                            }


                            }
                            break;

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 120, ruleCast_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleComparison() throws RecognitionException {
        EObject current = null;
        int entryRuleComparison_StartIndex = input.index();
        EObject iv_ruleComparison = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 121) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getComparisonRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleComparison=ruleComparison();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleComparison; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 121, entryRuleComparison_StartIndex); }
        }
        return current;
    }
    public final EObject ruleComparison() throws RecognitionException {
        EObject current = null;
        int ruleComparison_StartIndex = input.index();
        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        Token lv_op_2_3=null;
        Token lv_op_2_4=null;
        Token lv_op_2_5=null;
        Token lv_op_2_6=null;
        EObject this_Addition_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 122) ) { return current; }
            {
            {
            if ( state.backtracking==0 ) {

              			/* */
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getComparisonAccess().getAdditionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_44);
            this_Addition_0=ruleAddition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Addition_0;
              			afterParserOrEnumRuleCall();
              		
            }
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==34||(LA80_0>=107 && LA80_0<=110)) ) {
                alt80=1;
            }
            else if ( (LA80_0==83) ) {
                int LA80_2 = input.LA(2);

                if ( ((LA80_2>=RULE_ID && LA80_2<=RULE_KEYWORD)||LA80_2==29||LA80_2==32||LA80_2==36||(LA80_2>=38 && LA80_2<=81)||LA80_2==112||(LA80_2>=116 && LA80_2<=119)) ) {
                    alt80=1;
                }
            }
            switch (alt80) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						/* */
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						current = forceCreateModelElementAndSet(
                      							grammarAccess.getComparisonAccess().getBinaryOperatorLeftAction_1_0_0(),
                      							current);
                      					
                    }

                    }
                    {
                    {
                    int alt79=6;
                    switch ( input.LA(1) ) {
                    case 107:
                        {
                        alt79=1;
                        }
                        break;
                    case 34:
                        {
                        alt79=2;
                        }
                        break;
                    case 108:
                        {
                        alt79=3;
                        }
                        break;
                    case 109:
                        {
                        alt79=4;
                        }
                        break;
                    case 110:
                        {
                        alt79=5;
                        }
                        break;
                    case 83:
                        {
                        alt79=6;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 79, 0, input);

                        throw nvae;
                    }

                    switch (alt79) {
                        case 1 :
                            {
                            lv_op_2_1=(Token)match(input,107,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_1, grammarAccess.getComparisonAccess().getOpExclamationMarkEqualsSignKeyword_1_0_1_0_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getComparisonRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_1, null);
                              							
                            }

                            }
                            break;
                        case 2 :
                            {
                            lv_op_2_2=(Token)match(input,34,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_2, grammarAccess.getComparisonAccess().getOpEqualsSignKeyword_1_0_1_0_1());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getComparisonRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_2, null);
                              							
                            }

                            }
                            break;
                        case 3 :
                            {
                            lv_op_2_3=(Token)match(input,108,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_3, grammarAccess.getComparisonAccess().getOpGreaterThanSignEqualsSignKeyword_1_0_1_0_2());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getComparisonRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_3, null);
                              							
                            }

                            }
                            break;
                        case 4 :
                            {
                            lv_op_2_4=(Token)match(input,109,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_4, grammarAccess.getComparisonAccess().getOpLessThanSignEqualsSignKeyword_1_0_1_0_3());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getComparisonRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_4, null);
                              							
                            }

                            }
                            break;
                        case 5 :
                            {
                            lv_op_2_5=(Token)match(input,110,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_5, grammarAccess.getComparisonAccess().getOpLessThanSignKeyword_1_0_1_0_4());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getComparisonRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_5, null);
                              							
                            }

                            }
                            break;
                        case 6 :
                            {
                            lv_op_2_6=(Token)match(input,83,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_6, grammarAccess.getComparisonAccess().getOpGreaterThanSignKeyword_1_0_1_0_5());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getComparisonRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_6, null);
                              							
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getComparisonAccess().getRightAdditionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_right_3_0=ruleAddition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getComparisonRule());
                      						}
                      						set(
                      							current,
                      							"right",
                      							lv_right_3_0,
                      							"gama.core.lang.Gaml.Addition");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 122, ruleComparison_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleAddition() throws RecognitionException {
        EObject current = null;
        int entryRuleAddition_StartIndex = input.index();
        EObject iv_ruleAddition = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 123) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAdditionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAddition=ruleAddition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAddition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 123, entryRuleAddition_StartIndex); }
        }
        return current;
    }
    public final EObject ruleAddition() throws RecognitionException {
        EObject current = null;
        int ruleAddition_StartIndex = input.index();
        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_Multiplication_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 124) ) { return current; }
            {
            {
            if ( state.backtracking==0 ) {

              			/* */
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_45);
            this_Multiplication_0=ruleMultiplication();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Multiplication_0;
              			afterParserOrEnumRuleCall();
              		
            }
            loop82:
            do {
                int alt82=2;
                int LA82_0 = input.LA(1);

                if ( ((LA82_0>=111 && LA82_0<=112)) ) {
                    alt82=1;
                }


                switch (alt82) {
            	case 1 :
            	    {
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      						/* */
            	      					
            	    }
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getAdditionAccess().getBinaryOperatorLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }
            	    {
            	    {
            	    int alt81=2;
            	    int LA81_0 = input.LA(1);

            	    if ( (LA81_0==111) ) {
            	        alt81=1;
            	    }
            	    else if ( (LA81_0==112) ) {
            	        alt81=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 81, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt81) {
            	        case 1 :
            	            {
            	            lv_op_2_1=(Token)match(input,111,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_1, grammarAccess.getAdditionAccess().getOpPlusSignKeyword_1_0_1_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getAdditionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_1, null);
            	              							
            	            }

            	            }
            	            break;
            	        case 2 :
            	            {
            	            lv_op_2_2=(Token)match(input,112,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_2, grammarAccess.getAdditionAccess().getOpHyphenMinusKeyword_1_0_1_0_1());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getAdditionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_2, null);
            	              							
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_45);
            	    lv_right_3_0=ruleMultiplication();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getAdditionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"gama.core.lang.Gaml.Multiplication");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop82;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 124, ruleAddition_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleMultiplication() throws RecognitionException {
        EObject current = null;
        int entryRuleMultiplication_StartIndex = input.index();
        EObject iv_ruleMultiplication = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 125) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMultiplicationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMultiplication=ruleMultiplication();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMultiplication; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 125, entryRuleMultiplication_StartIndex); }
        }
        return current;
    }
    public final EObject ruleMultiplication() throws RecognitionException {
        EObject current = null;
        int ruleMultiplication_StartIndex = input.index();
        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_Exponentiation_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 126) ) { return current; }
            {
            {
            if ( state.backtracking==0 ) {

              			/* */
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getMultiplicationAccess().getExponentiationParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_46);
            this_Exponentiation_0=ruleExponentiation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Exponentiation_0;
              			afterParserOrEnumRuleCall();
              		
            }
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( ((LA84_0>=113 && LA84_0<=114)) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    {
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      						/* */
            	      					
            	    }
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getMultiplicationAccess().getBinaryOperatorLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }
            	    {
            	    {
            	    int alt83=2;
            	    int LA83_0 = input.LA(1);

            	    if ( (LA83_0==113) ) {
            	        alt83=1;
            	    }
            	    else if ( (LA83_0==114) ) {
            	        alt83=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 83, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt83) {
            	        case 1 :
            	            {
            	            lv_op_2_1=(Token)match(input,113,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_1, grammarAccess.getMultiplicationAccess().getOpAsteriskKeyword_1_0_1_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getMultiplicationRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_1, null);
            	              							
            	            }

            	            }
            	            break;
            	        case 2 :
            	            {
            	            lv_op_2_2=(Token)match(input,114,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_2, grammarAccess.getMultiplicationAccess().getOpSolidusKeyword_1_0_1_0_1());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getMultiplicationRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_2, null);
            	              							
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getMultiplicationAccess().getRightExponentiationParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_46);
            	    lv_right_3_0=ruleExponentiation();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getMultiplicationRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"gama.core.lang.Gaml.Exponentiation");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop84;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 126, ruleMultiplication_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleExponentiation() throws RecognitionException {
        EObject current = null;
        int entryRuleExponentiation_StartIndex = input.index();
        EObject iv_ruleExponentiation = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 127) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExponentiationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExponentiation=ruleExponentiation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExponentiation; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 127, entryRuleExponentiation_StartIndex); }
        }
        return current;
    }
    public final EObject ruleExponentiation() throws RecognitionException {
        EObject current = null;
        int ruleExponentiation_StartIndex = input.index();
        Token lv_op_2_0=null;
        EObject this_Binary_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 128) ) { return current; }
            {
            {
            if ( state.backtracking==0 ) {

              			/* */
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getExponentiationAccess().getBinaryParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_47);
            this_Binary_0=ruleBinary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Binary_0;
              			afterParserOrEnumRuleCall();
              		
            }
            loop85:
            do {
                int alt85=2;
                int LA85_0 = input.LA(1);

                if ( (LA85_0==115) ) {
                    alt85=1;
                }


                switch (alt85) {
            	case 1 :
            	    {
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      						/* */
            	      					
            	    }
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getExponentiationAccess().getBinaryOperatorLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }
            	    {
            	    {
            	    lv_op_2_0=(Token)match(input,115,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							newLeafNode(lv_op_2_0, grammarAccess.getExponentiationAccess().getOpCircumflexAccentKeyword_1_0_1_0());
            	      						
            	    }
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElement(grammarAccess.getExponentiationRule());
            	      							}
            	      							setWithLastConsumed(current, "op", lv_op_2_0, "^");
            	      						
            	    }

            	    }


            	    }


            	    }
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getExponentiationAccess().getRightBinaryParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_47);
            	    lv_right_3_0=ruleBinary();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getExponentiationRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"gama.core.lang.Gaml.Binary");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop85;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 128, ruleExponentiation_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleBinary() throws RecognitionException {
        EObject current = null;
        int entryRuleBinary_StartIndex = input.index();
        EObject iv_ruleBinary = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 129) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBinaryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBinary=ruleBinary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBinary; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 129, entryRuleBinary_StartIndex); }
        }
        return current;
    }
    public final EObject ruleBinary() throws RecognitionException {
        EObject current = null;
        int ruleBinary_StartIndex = input.index();
        EObject this_Unit_0 = null;

        AntlrDatatypeRuleToken lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 130) ) { return current; }
            {
            {
            if ( state.backtracking==0 ) {

              			/* */
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getBinaryAccess().getUnitParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_48);
            this_Unit_0=ruleUnit();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Unit_0;
              			afterParserOrEnumRuleCall();
              		
            }
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( (LA86_0==RULE_ID) ) {
                    int LA86_2 = input.LA(2);

                    if ( ((LA86_2>=RULE_ID && LA86_2<=RULE_KEYWORD)||LA86_2==29||LA86_2==32||LA86_2==36||(LA86_2>=38 && LA86_2<=81)||LA86_2==112||(LA86_2>=116 && LA86_2<=119)) ) {
                        alt86=1;
                    }


                }
                else if ( (LA86_0==36||(LA86_0>=38 && LA86_0<=81)) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    {
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      						/* */
            	      					
            	    }
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getBinaryAccess().getBinaryOperatorLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getBinaryAccess().getOpValid_IDParserRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_op_2_0=ruleValid_ID();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getBinaryRule());
            	      							}
            	      							set(
            	      								current,
            	      								"op",
            	      								lv_op_2_0,
            	      								"gama.core.lang.Gaml.Valid_ID");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getBinaryAccess().getRightUnitParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_48);
            	    lv_right_3_0=ruleUnit();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getBinaryRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"gama.core.lang.Gaml.Unit");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop86;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 130, ruleBinary_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;
        int entryRuleUnit_StartIndex = input.index();
        EObject iv_ruleUnit = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 131) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnitRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnit=ruleUnit();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnit; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 131, entryRuleUnit_StartIndex); }
        }
        return current;
    }
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;
        int ruleUnit_StartIndex = input.index();
        Token lv_op_2_0=null;
        EObject this_Unary_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 132) ) { return current; }
            {
            {
            if ( state.backtracking==0 ) {

              			/* */
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUnitAccess().getUnaryParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_49);
            this_Unary_0=ruleUnary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Unary_0;
              			afterParserOrEnumRuleCall();
              		
            }
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==116) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						/* */
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						current = forceCreateModelElementAndSet(
                      							grammarAccess.getUnitAccess().getUnitLeftAction_1_0_0(),
                      							current);
                      					
                    }

                    }
                    {
                    {
                    lv_op_2_0=(Token)match(input,116,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_op_2_0, grammarAccess.getUnitAccess().getOpNumberSignKeyword_1_0_1_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getUnitRule());
                      							}
                      							setWithLastConsumed(current, "op", lv_op_2_0, "#");
                      						
                    }

                    }


                    }


                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getUnitAccess().getRightUnitRefParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_right_3_0=ruleUnitRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getUnitRule());
                      						}
                      						set(
                      							current,
                      							"right",
                      							lv_right_3_0,
                      							"gama.core.lang.Gaml.UnitRef");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 132, ruleUnit_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleUnary() throws RecognitionException {
        EObject current = null;
        int entryRuleUnary_StartIndex = input.index();
        EObject iv_ruleUnary = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 133) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnaryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnary=ruleUnary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnary; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 133, entryRuleUnary_StartIndex); }
        }
        return current;
    }
    public final EObject ruleUnary() throws RecognitionException {
        EObject current = null;
        int ruleUnary_StartIndex = input.index();
        Token lv_op_2_0=null;
        Token lv_op_4_1=null;
        Token lv_op_4_2=null;
        Token lv_op_4_3=null;
        EObject this_Access_0 = null;

        EObject lv_right_3_0 = null;

        EObject lv_right_5_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 134) ) { return current; }
            {
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( ((LA90_0>=RULE_ID && LA90_0<=RULE_KEYWORD)||LA90_0==29||LA90_0==32||LA90_0==36||(LA90_0>=38 && LA90_0<=81)||LA90_0==119) ) {
                alt90=1;
            }
            else if ( (LA90_0==112||(LA90_0>=116 && LA90_0<=118)) ) {
                alt90=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 90, 0, input);

                throw nvae;
            }
            switch (alt90) {
                case 1 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getUnaryAccess().getAccessParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_Access_0=ruleAccess();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_Access_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					/* */
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getUnaryAccess().getUnaryAction_1_0(),
                      						current);
                      				
                    }

                    }
                    int alt89=2;
                    int LA89_0 = input.LA(1);

                    if ( (LA89_0==116) ) {
                        alt89=1;
                    }
                    else if ( (LA89_0==112||(LA89_0>=117 && LA89_0<=118)) ) {
                        alt89=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 89, 0, input);

                        throw nvae;
                    }
                    switch (alt89) {
                        case 1 :
                            {
                            {
                            {
                            {
                            lv_op_2_0=(Token)match(input,116,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_0, grammarAccess.getUnaryAccess().getOpNumberSignKeyword_1_1_0_0_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getUnaryRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_0, "#");
                              							
                            }

                            }


                            }
                            {
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getUnaryAccess().getRightUnitRefParserRuleCall_1_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_right_3_0=ruleUnitRef();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getUnaryRule());
                              								}
                              								set(
                              									current,
                              									"right",
                              									lv_right_3_0,
                              									"gama.core.lang.Gaml.UnitRef");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }


                            }


                            }
                            break;
                        case 2 :
                            {
                            {
                            {
                            {
                            int alt88=3;
                            switch ( input.LA(1) ) {
                            case 112:
                                {
                                alt88=1;
                                }
                                break;
                            case 117:
                                {
                                alt88=2;
                                }
                                break;
                            case 118:
                                {
                                alt88=3;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 88, 0, input);

                                throw nvae;
                            }

                            switch (alt88) {
                                case 1 :
                                    {
                                    lv_op_4_1=(Token)match(input,112,FOLLOW_5); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_op_4_1, grammarAccess.getUnaryAccess().getOpHyphenMinusKeyword_1_1_1_0_0_0());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getUnaryRule());
                                      									}
                                      									setWithLastConsumed(current, "op", lv_op_4_1, null);
                                      								
                                    }

                                    }
                                    break;
                                case 2 :
                                    {
                                    lv_op_4_2=(Token)match(input,117,FOLLOW_5); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_op_4_2, grammarAccess.getUnaryAccess().getOpExclamationMarkKeyword_1_1_1_0_0_1());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getUnaryRule());
                                      									}
                                      									setWithLastConsumed(current, "op", lv_op_4_2, null);
                                      								
                                    }

                                    }
                                    break;
                                case 3 :
                                    {
                                    lv_op_4_3=(Token)match(input,118,FOLLOW_5); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_op_4_3, grammarAccess.getUnaryAccess().getOpNotKeyword_1_1_1_0_0_2());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getUnaryRule());
                                      									}
                                      									setWithLastConsumed(current, "op", lv_op_4_3, null);
                                      								
                                    }

                                    }
                                    break;

                            }


                            }


                            }
                            {
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getUnaryAccess().getRightUnaryParserRuleCall_1_1_1_1_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_right_5_0=ruleUnary();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getUnaryRule());
                              								}
                              								set(
                              									current,
                              									"right",
                              									lv_right_5_0,
                              									"gama.core.lang.Gaml.Unary");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 134, ruleUnary_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleAccess() throws RecognitionException {
        EObject current = null;
        int entryRuleAccess_StartIndex = input.index();
        EObject iv_ruleAccess = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 135) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAccessRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAccess=ruleAccess();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAccess; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 135, entryRuleAccess_StartIndex); }
        }
        return current;
    }
    public final EObject ruleAccess() throws RecognitionException {
        EObject current = null;
        int ruleAccess_StartIndex = input.index();
        Token lv_op_2_0=null;
        Token otherlv_4=null;
        Token lv_op_5_0=null;
        EObject this_Primary_0 = null;

        EObject lv_right_3_0 = null;

        EObject lv_right_6_1 = null;

        EObject lv_right_6_2 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 136) ) { return current; }
            {
            {
            if ( state.backtracking==0 ) {

              			/* */
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getAccessAccess().getPrimaryParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_50);
            this_Primary_0=rulePrimary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Primary_0;
              			afterParserOrEnumRuleCall();
              		
            }
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==119||LA94_0==121) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    {
            	    {
            	    if ( state.backtracking==0 ) {

            	      					/* */
            	      				
            	    }
            	    if ( state.backtracking==0 ) {

            	      					current = forceCreateModelElementAndSet(
            	      						grammarAccess.getAccessAccess().getAccessLeftAction_1_0(),
            	      						current);
            	      				
            	    }

            	    }
            	    int alt93=2;
            	    int LA93_0 = input.LA(1);

            	    if ( (LA93_0==119) ) {
            	        alt93=1;
            	    }
            	    else if ( (LA93_0==121) ) {
            	        alt93=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 93, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt93) {
            	        case 1 :
            	            {
            	            {
            	            {
            	            {
            	            lv_op_2_0=(Token)match(input,119,FOLLOW_51); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_0, grammarAccess.getAccessAccess().getOpLeftSquareBracketKeyword_1_1_0_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getAccessRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_0, "[");
            	              							
            	            }

            	            }


            	            }
            	            int alt91=2;
            	            int LA91_0 = input.LA(1);

            	            if ( ((LA91_0>=RULE_ID && LA91_0<=RULE_KEYWORD)||LA91_0==29||LA91_0==32||LA91_0==36||(LA91_0>=38 && LA91_0<=81)||(LA91_0>=90 && LA91_0<=101)||LA91_0==112||(LA91_0>=116 && LA91_0<=119)) ) {
            	                alt91=1;
            	            }
            	            switch (alt91) {
            	                case 1 :
            	                    {
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      								newCompositeNode(grammarAccess.getAccessAccess().getRightExpressionListParserRuleCall_1_1_0_1_0());
            	                      							
            	                    }
            	                    pushFollow(FOLLOW_52);
            	                    lv_right_3_0=ruleExpressionList();

            	                    state._fsp--;
            	                    if (state.failed) return current;
            	                    if ( state.backtracking==0 ) {

            	                      								if (current==null) {
            	                      									current = createModelElementForParent(grammarAccess.getAccessRule());
            	                      								}
            	                      								set(
            	                      									current,
            	                      									"right",
            	                      									lv_right_3_0,
            	                      									"gama.core.lang.Gaml.ExpressionList");
            	                      								afterParserOrEnumRuleCall();
            	                      							
            	                    }

            	                    }


            	                    }
            	                    break;

            	            }

            	            otherlv_4=(Token)match(input,120,FOLLOW_50); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						newLeafNode(otherlv_4, grammarAccess.getAccessAccess().getRightSquareBracketKeyword_1_1_0_2());
            	              					
            	            }

            	            }


            	            }
            	            break;
            	        case 2 :
            	            {
            	            {
            	            {
            	            {
            	            lv_op_5_0=(Token)match(input,121,FOLLOW_13); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_5_0, grammarAccess.getAccessAccess().getOpFullStopKeyword_1_1_1_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getAccessRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_5_0, ".");
            	              							
            	            }

            	            }


            	            }
            	            {
            	            {
            	            int alt92=2;
            	            int LA92_0 = input.LA(1);

            	            if ( (LA92_0==RULE_ID||LA92_0==36||(LA92_0>=38 && LA92_0<=81)) ) {
            	                alt92=1;
            	            }
            	            else if ( (LA92_0==RULE_STRING) ) {
            	                alt92=2;
            	            }
            	            else {
            	                if (state.backtracking>0) {state.failed=true; return current;}
            	                NoViableAltException nvae =
            	                    new NoViableAltException("", 92, 0, input);

            	                throw nvae;
            	            }
            	            switch (alt92) {
            	                case 1 :
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      									newCompositeNode(grammarAccess.getAccessAccess().getRightAbstractRefParserRuleCall_1_1_1_1_0_0());
            	                      								
            	                    }
            	                    pushFollow(FOLLOW_50);
            	                    lv_right_6_1=ruleAbstractRef();

            	                    state._fsp--;
            	                    if (state.failed) return current;
            	                    if ( state.backtracking==0 ) {

            	                      									if (current==null) {
            	                      										current = createModelElementForParent(grammarAccess.getAccessRule());
            	                      									}
            	                      									set(
            	                      										current,
            	                      										"right",
            	                      										lv_right_6_1,
            	                      										"gama.core.lang.Gaml.AbstractRef");
            	                      									afterParserOrEnumRuleCall();
            	                      								
            	                    }

            	                    }
            	                    break;
            	                case 2 :
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      									newCompositeNode(grammarAccess.getAccessAccess().getRightStringLiteralParserRuleCall_1_1_1_1_0_1());
            	                      								
            	                    }
            	                    pushFollow(FOLLOW_50);
            	                    lv_right_6_2=ruleStringLiteral();

            	                    state._fsp--;
            	                    if (state.failed) return current;
            	                    if ( state.backtracking==0 ) {

            	                      									if (current==null) {
            	                      										current = createModelElementForParent(grammarAccess.getAccessRule());
            	                      									}
            	                      									set(
            	                      										current,
            	                      										"right",
            	                      										lv_right_6_2,
            	                      										"gama.core.lang.Gaml.StringLiteral");
            	                      									afterParserOrEnumRuleCall();
            	                      								
            	                    }

            	                    }
            	                    break;

            	            }


            	            }


            	            }


            	            }


            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop94;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 136, ruleAccess_StartIndex); }
        }
        return current;
    }
    public final EObject entryRulePrimary() throws RecognitionException {
        EObject current = null;
        int entryRulePrimary_StartIndex = input.index();
        EObject iv_rulePrimary = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 137) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPrimaryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePrimary=rulePrimary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePrimary; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 137, entryRulePrimary_StartIndex); }
        }
        return current;
    }
    public final EObject rulePrimary() throws RecognitionException {
        EObject current = null;
        int rulePrimary_StartIndex = input.index();
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token lv_op_12_0=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        EObject this_TerminalExpression_0 = null;

        EObject this_AbstractRef_1 = null;

        EObject this_ExpressionList_3 = null;

        EObject lv_exprs_7_0 = null;

        EObject lv_left_11_0 = null;

        EObject lv_right_13_0 = null;

        EObject lv_z_15_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 138) ) { return current; }
            {
            int alt97=5;
            switch ( input.LA(1) ) {
            case RULE_STRING:
            case RULE_INTEGER:
            case RULE_DOUBLE:
            case RULE_BOOLEAN:
            case RULE_KEYWORD:
                {
                alt97=1;
                }
                break;
            case RULE_ID:
            case 36:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
                {
                alt97=2;
                }
                break;
            case 29:
                {
                alt97=3;
                }
                break;
            case 119:
                {
                alt97=4;
                }
                break;
            case 32:
                {
                alt97=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 97, 0, input);

                throw nvae;
            }

            switch (alt97) {
                case 1 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getPrimaryAccess().getTerminalExpressionParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TerminalExpression_0=ruleTerminalExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TerminalExpression_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getPrimaryAccess().getAbstractRefParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_AbstractRef_1=ruleAbstractRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_AbstractRef_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    {
                    {
                    otherlv_2=(Token)match(input,29,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				/* */
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getPrimaryAccess().getExpressionListParserRuleCall_2_1());
                      			
                    }
                    pushFollow(FOLLOW_26);
                    this_ExpressionList_3=ruleExpressionList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_ExpressionList_3;
                      				afterParserOrEnumRuleCall();
                      			
                    }
                    otherlv_4=(Token)match(input,30,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_2_2());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    {
                    {
                    otherlv_5=(Token)match(input,119,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getPrimaryAccess().getLeftSquareBracketKeyword_3_0());
                      			
                    }
                    {
                    if ( state.backtracking==0 ) {

                      					/* */
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getPrimaryAccess().getArrayAction_3_1(),
                      						current);
                      				
                    }

                    }
                    int alt95=2;
                    int LA95_0 = input.LA(1);

                    if ( ((LA95_0>=RULE_ID && LA95_0<=RULE_KEYWORD)||LA95_0==29||LA95_0==32||LA95_0==36||(LA95_0>=38 && LA95_0<=81)||(LA95_0>=90 && LA95_0<=101)||LA95_0==112||(LA95_0>=116 && LA95_0<=119)) ) {
                        alt95=1;
                    }
                    switch (alt95) {
                        case 1 :
                            {
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getPrimaryAccess().getExprsExpressionListParserRuleCall_3_2_0());
                              					
                            }
                            pushFollow(FOLLOW_52);
                            lv_exprs_7_0=ruleExpressionList();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElementForParent(grammarAccess.getPrimaryRule());
                              						}
                              						set(
                              							current,
                              							"exprs",
                              							lv_exprs_7_0,
                              							"gama.core.lang.Gaml.ExpressionList");
                              						afterParserOrEnumRuleCall();
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_8=(Token)match(input,120,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getPrimaryAccess().getRightSquareBracketKeyword_3_3());
                      			
                    }

                    }


                    }
                    break;
                case 5 :
                    {
                    {
                    otherlv_9=(Token)match(input,32,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getPrimaryAccess().getLeftCurlyBracketKeyword_4_0());
                      			
                    }
                    {
                    if ( state.backtracking==0 ) {

                      					/* */
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getPrimaryAccess().getPointAction_4_1(),
                      						current);
                      				
                    }

                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPrimaryAccess().getLeftExpressionParserRuleCall_4_2_0());
                      					
                    }
                    pushFollow(FOLLOW_54);
                    lv_left_11_0=ruleExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPrimaryRule());
                      						}
                      						set(
                      							current,
                      							"left",
                      							lv_left_11_0,
                      							"gama.core.lang.Gaml.Expression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }
                    {
                    {
                    lv_op_12_0=(Token)match(input,88,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_12_0, grammarAccess.getPrimaryAccess().getOpCommaKeyword_4_3_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getPrimaryRule());
                      						}
                      						setWithLastConsumed(current, "op", lv_op_12_0, ",");
                      					
                    }

                    }


                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPrimaryAccess().getRightExpressionParserRuleCall_4_4_0());
                      					
                    }
                    pushFollow(FOLLOW_55);
                    lv_right_13_0=ruleExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPrimaryRule());
                      						}
                      						set(
                      							current,
                      							"right",
                      							lv_right_13_0,
                      							"gama.core.lang.Gaml.Expression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }
                    int alt96=2;
                    int LA96_0 = input.LA(1);

                    if ( (LA96_0==88) ) {
                        alt96=1;
                    }
                    switch (alt96) {
                        case 1 :
                            {
                            otherlv_14=(Token)match(input,88,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_14, grammarAccess.getPrimaryAccess().getCommaKeyword_4_5_0());
                              				
                            }
                            {
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getPrimaryAccess().getZExpressionParserRuleCall_4_5_1_0());
                              						
                            }
                            pushFollow(FOLLOW_56);
                            lv_z_15_0=ruleExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getPrimaryRule());
                              							}
                              							set(
                              								current,
                              								"z",
                              								lv_z_15_0,
                              								"gama.core.lang.Gaml.Expression");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    otherlv_16=(Token)match(input,33,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getPrimaryAccess().getRightCurlyBracketKeyword_4_6());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 138, rulePrimary_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleAbstractRef() throws RecognitionException {
        EObject current = null;
        int entryRuleAbstractRef_StartIndex = input.index();
        EObject iv_ruleAbstractRef = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 139) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAbstractRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAbstractRef=ruleAbstractRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAbstractRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 139, entryRuleAbstractRef_StartIndex); }
        }
        return current;
    }
    public final EObject ruleAbstractRef() throws RecognitionException {
        EObject current = null;
        int ruleAbstractRef_StartIndex = input.index();
        EObject this_Function_0 = null;

        EObject this_VariableRef_1 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 140) ) { return current; }
            {
            int alt98=2;
            alt98 = dfa98.predict(input);
            switch (alt98) {
                case 1 :
                    {
                    {
                    if ( state.backtracking==0 ) {

                      				/* */
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getAbstractRefAccess().getFunctionParserRuleCall_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_Function_0=ruleFunction();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_Function_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAbstractRefAccess().getVariableRefParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_VariableRef_1=ruleVariableRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_VariableRef_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 140, ruleAbstractRef_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;
        int entryRuleFunction_StartIndex = input.index();
        EObject iv_ruleFunction = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 141) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFunctionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFunction=ruleFunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFunction; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 141, entryRuleFunction_StartIndex); }
        }
        return current;
    }
    public final EObject ruleFunction() throws RecognitionException {
        EObject current = null;
        int ruleFunction_StartIndex = input.index();
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_left_1_0 = null;

        EObject lv_type_2_0 = null;

        EObject lv_right_4_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 142) ) { return current; }
            {
            {
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getFunctionAccess().getFunctionAction_0(),
              					current);
              			
            }

            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getFunctionAccess().getLeftActionRefParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_57);
            lv_left_1_0=ruleActionRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getFunctionRule());
              					}
              					set(
              						current,
              						"left",
              						lv_left_1_0,
              						"gama.core.lang.Gaml.ActionRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==110) ) {
                alt99=1;
            }
            switch (alt99) {
                case 1 :
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getFunctionAccess().getTypeTypeInfoParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_58);
                    lv_type_2_0=ruleTypeInfo();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getFunctionRule());
                      					}
                      					set(
                      						current,
                      						"type",
                      						lv_type_2_0,
                      						"gama.core.lang.Gaml.TypeInfo");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,29,FOLLOW_59); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_3());
              		
            }
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( ((LA100_0>=RULE_ID && LA100_0<=RULE_KEYWORD)||LA100_0==29||LA100_0==32||LA100_0==36||(LA100_0>=38 && LA100_0<=81)||(LA100_0>=90 && LA100_0<=101)||LA100_0==112||(LA100_0>=116 && LA100_0<=119)) ) {
                alt100=1;
            }
            switch (alt100) {
                case 1 :
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getFunctionAccess().getRightExpressionListParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_26);
                    lv_right_4_0=ruleExpressionList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getFunctionRule());
                      					}
                      					set(
                      						current,
                      						"right",
                      						lv_right_4_0,
                      						"gama.core.lang.Gaml.ExpressionList");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_5=(Token)match(input,30,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_5());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 142, ruleFunction_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleExpressionList() throws RecognitionException {
        EObject current = null;
        int entryRuleExpressionList_StartIndex = input.index();
        EObject iv_ruleExpressionList = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 143) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExpressionListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExpressionList=ruleExpressionList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExpressionList; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 143, entryRuleExpressionList_StartIndex); }
        }
        return current;
    }
    public final EObject ruleExpressionList() throws RecognitionException {
        EObject current = null;
        int ruleExpressionList_StartIndex = input.index();
        Token otherlv_1=null;
        Token otherlv_4=null;
        EObject lv_exprs_0_0 = null;

        EObject lv_exprs_2_0 = null;

        EObject lv_exprs_3_0 = null;

        EObject lv_exprs_5_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 144) ) { return current; }
            {
            int alt103=2;
            alt103 = dfa103.predict(input);
            switch (alt103) {
                case 1 :
                    {
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getExpressionListAccess().getExprsExpressionParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_34);
                    lv_exprs_0_0=ruleExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getExpressionListRule());
                      						}
                      						add(
                      							current,
                      							"exprs",
                      							lv_exprs_0_0,
                      							"gama.core.lang.Gaml.Expression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }
                    loop101:
                    do {
                        int alt101=2;
                        int LA101_0 = input.LA(1);

                        if ( (LA101_0==88) ) {
                            alt101=1;
                        }


                        switch (alt101) {
                    	case 1 :
                    	    {
                    	    otherlv_1=(Token)match(input,88,FOLLOW_5); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_1, grammarAccess.getExpressionListAccess().getCommaKeyword_0_1_0());
                    	      				
                    	    }
                    	    {
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getExpressionListAccess().getExprsExpressionParserRuleCall_0_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_34);
                    	    lv_exprs_2_0=ruleExpression();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getExpressionListRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"exprs",
                    	      								lv_exprs_2_0,
                    	      								"gama.core.lang.Gaml.Expression");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop101;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    {
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getExpressionListAccess().getExprsParameterParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_34);
                    lv_exprs_3_0=ruleParameter();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getExpressionListRule());
                      						}
                      						add(
                      							current,
                      							"exprs",
                      							lv_exprs_3_0,
                      							"gama.core.lang.Gaml.Parameter");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }
                    loop102:
                    do {
                        int alt102=2;
                        int LA102_0 = input.LA(1);

                        if ( (LA102_0==88) ) {
                            alt102=1;
                        }


                        switch (alt102) {
                    	case 1 :
                    	    {
                    	    otherlv_4=(Token)match(input,88,FOLLOW_53); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_4, grammarAccess.getExpressionListAccess().getCommaKeyword_1_1_0());
                    	      				
                    	    }
                    	    {
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getExpressionListAccess().getExprsParameterParserRuleCall_1_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_34);
                    	    lv_exprs_5_0=ruleParameter();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getExpressionListRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"exprs",
                    	      								lv_exprs_5_0,
                    	      								"gama.core.lang.Gaml.Parameter");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop102;
                        }
                    } while (true);


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 144, ruleExpressionList_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;
        int entryRuleParameter_StartIndex = input.index();
        EObject iv_ruleParameter = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 145) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleParameter=ruleParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleParameter; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 145, entryRuleParameter_StartIndex); }
        }
        return current;
    }
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;
        int ruleParameter_StartIndex = input.index();
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_builtInFacetKey_1_1 = null;

        AntlrDatatypeRuleToken lv_builtInFacetKey_1_2 = null;

        AntlrDatatypeRuleToken lv_builtInFacetKey_1_3 = null;

        EObject lv_left_2_0 = null;

        EObject lv_right_4_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 146) ) { return current; }
            {
            {
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getParameterAccess().getParameterAction_0(),
              					current);
              			
            }

            }
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( ((LA105_0>=90 && LA105_0<=101)) ) {
                alt105=1;
            }
            else if ( (LA105_0==RULE_ID||LA105_0==36||(LA105_0>=38 && LA105_0<=81)) ) {
                alt105=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 105, 0, input);

                throw nvae;
            }
            switch (alt105) {
                case 1 :
                    {
                    {
                    {
                    int alt104=3;
                    switch ( input.LA(1) ) {
                    case 90:
                        {
                        alt104=1;
                        }
                        break;
                    case 91:
                    case 92:
                    case 93:
                    case 94:
                    case 95:
                    case 96:
                    case 97:
                    case 98:
                    case 99:
                        {
                        alt104=2;
                        }
                        break;
                    case 100:
                    case 101:
                        {
                        alt104=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 104, 0, input);

                        throw nvae;
                    }

                    switch (alt104) {
                        case 1 :
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getParameterAccess().getBuiltInFacetKeyDefinitionFacetKeyParserRuleCall_1_0_0_0());
                              						
                            }
                            pushFollow(FOLLOW_5);
                            lv_builtInFacetKey_1_1=ruleDefinitionFacetKey();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getParameterRule());
                              							}
                              							set(
                              								current,
                              								"builtInFacetKey",
                              								lv_builtInFacetKey_1_1,
                              								"gama.core.lang.Gaml.DefinitionFacetKey");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }
                            break;
                        case 2 :
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getParameterAccess().getBuiltInFacetKeySpecialFacetKeyParserRuleCall_1_0_0_1());
                              						
                            }
                            pushFollow(FOLLOW_5);
                            lv_builtInFacetKey_1_2=ruleSpecialFacetKey();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getParameterRule());
                              							}
                              							set(
                              								current,
                              								"builtInFacetKey",
                              								lv_builtInFacetKey_1_2,
                              								"gama.core.lang.Gaml.SpecialFacetKey");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }
                            break;
                        case 3 :
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getParameterAccess().getBuiltInFacetKeyActionFacetKeyParserRuleCall_1_0_0_2());
                              						
                            }
                            pushFollow(FOLLOW_5);
                            lv_builtInFacetKey_1_3=ruleActionFacetKey();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getParameterRule());
                              							}
                              							set(
                              								current,
                              								"builtInFacetKey",
                              								lv_builtInFacetKey_1_3,
                              								"gama.core.lang.Gaml.ActionFacetKey");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getParameterAccess().getLeftVariableRefParserRuleCall_1_1_0_0());
                      						
                    }
                    pushFollow(FOLLOW_36);
                    lv_left_2_0=ruleVariableRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getParameterRule());
                      							}
                      							set(
                      								current,
                      								"left",
                      								lv_left_2_0,
                      								"gama.core.lang.Gaml.VariableRef");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }

                    otherlv_3=(Token)match(input,89,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getParameterAccess().getColonKeyword_1_1_1());
                      				
                    }

                    }


                    }
                    break;

            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterAccess().getRightExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_right_4_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getParameterRule());
              					}
              					set(
              						current,
              						"right",
              						lv_right_4_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 146, ruleParameter_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleUnitRef() throws RecognitionException {
        EObject current = null;
        int entryRuleUnitRef_StartIndex = input.index();
        EObject iv_ruleUnitRef = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 147) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnitRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnitRef=ruleUnitRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnitRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 147, entryRuleUnitRef_StartIndex); }
        }
        return current;
    }
    public final EObject ruleUnitRef() throws RecognitionException {
        EObject current = null;
        int ruleUnitRef_StartIndex = input.index();
        Token otherlv_1=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 148) ) { return current; }
            {
            {
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getUnitRefAccess().getUnitNameAction_0(),
              					current);
              			
            }

            }
            {
            {
            if ( state.backtracking==0 ) {

              					/* */
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getUnitRefRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getUnitRefAccess().getRefUnitFakeDefinitionCrossReference_1_0());
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 148, ruleUnitRef_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleVariableRef() throws RecognitionException {
        EObject current = null;
        int entryRuleVariableRef_StartIndex = input.index();
        EObject iv_ruleVariableRef = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 149) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVariableRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVariableRef=ruleVariableRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVariableRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 149, entryRuleVariableRef_StartIndex); }
        }
        return current;
    }
    public final EObject ruleVariableRef() throws RecognitionException {
        EObject current = null;
        int ruleVariableRef_StartIndex = input.index();

        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 150) ) { return current; }
            {
            {
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVariableRefAccess().getVariableRefAction_0(),
              					current);
              			
            }

            }
            {
            {
            if ( state.backtracking==0 ) {

              					/* */
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getVariableRefRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getVariableRefAccess().getRefVarDefinitionCrossReference_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 150, ruleVariableRef_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleTypeRef() throws RecognitionException {
        EObject current = null;
        int entryRuleTypeRef_StartIndex = input.index();
        EObject iv_ruleTypeRef = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 151) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeRef=ruleTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 151, entryRuleTypeRef_StartIndex); }
        }
        return current;
    }
    public final EObject ruleTypeRef() throws RecognitionException {
        EObject current = null;
        int ruleTypeRef_StartIndex = input.index();
        Token otherlv_1=null;
        Token otherlv_4=null;
        EObject lv_parameter_2_0 = null;

        EObject lv_parameter_5_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 152) ) { return current; }
            {
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==RULE_ID) ) {
                alt107=1;
            }
            else if ( (LA107_0==38) ) {
                alt107=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 107, 0, input);

                throw nvae;
            }
            switch (alt107) {
                case 1 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					/* */
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTypeRefAccess().getTypeRefAction_0_0(),
                      						current);
                      				
                    }

                    }
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      							/* */
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getTypeRefRule());
                      							}
                      						
                    }
                    otherlv_1=(Token)match(input,RULE_ID,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(otherlv_1, grammarAccess.getTypeRefAccess().getRefTypeDefinitionCrossReference_0_1_0_0());
                      						
                    }

                    }


                    }
                    int alt106=2;
                    int LA106_0 = input.LA(1);

                    if ( (LA106_0==110) ) {
                        alt106=1;
                    }
                    switch (alt106) {
                        case 1 :
                            {
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getTypeRefAccess().getParameterTypeInfoParserRuleCall_0_1_1_0());
                              						
                            }
                            pushFollow(FOLLOW_2);
                            lv_parameter_2_0=ruleTypeInfo();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getTypeRefRule());
                              							}
                              							set(
                              								current,
                              								"parameter",
                              								lv_parameter_2_0,
                              								"gama.core.lang.Gaml.TypeInfo");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }
                            break;

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					/* */
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTypeRefAccess().getTypeRefAction_1_0(),
                      						current);
                      				
                    }

                    }
                    {
                    otherlv_4=(Token)match(input,38,FOLLOW_61); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_4, grammarAccess.getTypeRefAccess().getSpeciesKeyword_1_1_0());
                      				
                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getTypeRefAccess().getParameterTypeInfoParserRuleCall_1_1_1_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    lv_parameter_5_0=ruleTypeInfo();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getTypeRefRule());
                      							}
                      							set(
                      								current,
                      								"parameter",
                      								lv_parameter_5_0,
                      								"gama.core.lang.Gaml.TypeInfo");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 152, ruleTypeRef_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleTypeInfo() throws RecognitionException {
        EObject current = null;
        int entryRuleTypeInfo_StartIndex = input.index();
        EObject iv_ruleTypeInfo = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 153) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeInfoRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeInfo=ruleTypeInfo();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeInfo; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 153, entryRuleTypeInfo_StartIndex); }
        }
        return current;
    }
    public final EObject ruleTypeInfo() throws RecognitionException {
        EObject current = null;
        int ruleTypeInfo_StartIndex = input.index();
        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_first_1_0 = null;

        EObject lv_second_3_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 154) ) { return current; }
            {
            {
            otherlv_0=(Token)match(input,110,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getTypeInfoAccess().getLessThanSignKeyword_0());
              		
            }
            {
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTypeInfoAccess().getFirstTypeRefParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_62);
            lv_first_1_0=ruleTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTypeInfoRule());
              					}
              					set(
              						current,
              						"first",
              						lv_first_1_0,
              						"gama.core.lang.Gaml.TypeRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==88) ) {
                alt108=1;
            }
            switch (alt108) {
                case 1 :
                    {
                    otherlv_2=(Token)match(input,88,FOLLOW_25); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getTypeInfoAccess().getCommaKeyword_2_0());
                      			
                    }
                    {
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTypeInfoAccess().getSecondTypeRefParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_second_3_0=ruleTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTypeInfoRule());
                      						}
                      						set(
                      							current,
                      							"second",
                      							lv_second_3_0,
                      							"gama.core.lang.Gaml.TypeRef");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }
            {
            otherlv_4=(Token)match(input,83,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_4, grammarAccess.getTypeInfoAccess().getGreaterThanSignKeyword_3());
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 154, ruleTypeInfo_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleActionRef() throws RecognitionException {
        EObject current = null;
        int entryRuleActionRef_StartIndex = input.index();
        EObject iv_ruleActionRef = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 155) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActionRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleActionRef=ruleActionRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActionRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 155, entryRuleActionRef_StartIndex); }
        }
        return current;
    }
    public final EObject ruleActionRef() throws RecognitionException {
        EObject current = null;
        int ruleActionRef_StartIndex = input.index();

        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 156) ) { return current; }
            {
            {
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getActionRefAccess().getActionRefAction_0(),
              					current);
              			
            }

            }
            {
            {
            if ( state.backtracking==0 ) {

              					/* */
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getActionRefRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getActionRefAccess().getRefActionDefinitionCrossReference_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 156, ruleActionRef_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleEquationRef() throws RecognitionException {
        EObject current = null;
        int entryRuleEquationRef_StartIndex = input.index();
        EObject iv_ruleEquationRef = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 157) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getEquationRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleEquationRef=ruleEquationRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleEquationRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 157, entryRuleEquationRef_StartIndex); }
        }
        return current;
    }
    public final EObject ruleEquationRef() throws RecognitionException {
        EObject current = null;
        int ruleEquationRef_StartIndex = input.index();

        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 158) ) { return current; }
            {
            {
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getEquationRefAccess().getEquationRefAction_0(),
              					current);
              			
            }

            }
            {
            {
            if ( state.backtracking==0 ) {

              					/* */
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getEquationRefRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getEquationRefAccess().getRefEquationDefinitionCrossReference_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 158, ruleEquationRef_StartIndex); }
        }
        return current;
    }
    public final String entryRuleValid_ID() throws RecognitionException {
        String current = null;
        int entryRuleValid_ID_StartIndex = input.index();
        AntlrDatatypeRuleToken iv_ruleValid_ID = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 159) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getValid_IDRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleValid_ID=ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleValid_ID.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 159, entryRuleValid_ID_StartIndex); }
        }
        return current;
    }
    public final AntlrDatatypeRuleToken ruleValid_ID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();
        int ruleValid_ID_StartIndex = input.index();
        Token this_ID_5=null;
        AntlrDatatypeRuleToken this__SpeciesKey_0 = null;

        AntlrDatatypeRuleToken this__DoKey_1 = null;

        AntlrDatatypeRuleToken this__ReflexKey_2 = null;

        AntlrDatatypeRuleToken this__1Expr_Facets_BlockOrEnd_Key_3 = null;

        AntlrDatatypeRuleToken this__EquationsKey_4 = null;

        AntlrDatatypeRuleToken this__ExperimentKey_6 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 160) ) { return current; }
            {
            int alt109=7;
            switch ( input.LA(1) ) {
            case 38:
            case 39:
                {
                alt109=1;
                }
                break;
            case 77:
            case 78:
                {
                alt109=2;
                }
                break;
            case 79:
            case 80:
            case 81:
                {
                alt109=3;
                }
                break;
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
                {
                alt109=4;
                }
                break;
            case 36:
                {
                alt109=5;
                }
                break;
            case RULE_ID:
                {
                alt109=6;
                }
                break;
            case 40:
                {
                alt109=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 109, 0, input);

                throw nvae;
            }

            switch (alt109) {
                case 1 :
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getValid_IDAccess().get_SpeciesKeyParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__SpeciesKey_0=rule_SpeciesKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__SpeciesKey_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getValid_IDAccess().get_DoKeyParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__DoKey_1=rule_DoKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__DoKey_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getValid_IDAccess().get_ReflexKeyParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__ReflexKey_2=rule_ReflexKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__ReflexKey_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getValid_IDAccess().get_1Expr_Facets_BlockOrEnd_KeyParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__1Expr_Facets_BlockOrEnd_Key_3=rule_1Expr_Facets_BlockOrEnd_Key();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__1Expr_Facets_BlockOrEnd_Key_3);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getValid_IDAccess().get_EquationsKeyParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__EquationsKey_4=rule_EquationsKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__EquationsKey_4);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 6 :
                    {
                    this_ID_5=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_ID_5);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_ID_5, grammarAccess.getValid_IDAccess().getIDTerminalRuleCall_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getValid_IDAccess().get_ExperimentKeyParserRuleCall_6());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__ExperimentKey_6=rule_ExperimentKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__ExperimentKey_6);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 160, ruleValid_ID_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleTerminalExpression() throws RecognitionException {
        EObject current = null;
        int entryRuleTerminalExpression_StartIndex = input.index();
        EObject iv_ruleTerminalExpression = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 161) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTerminalExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTerminalExpression=ruleTerminalExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTerminalExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 161, entryRuleTerminalExpression_StartIndex); }
        }
        return current;
    }
    public final EObject ruleTerminalExpression() throws RecognitionException {
        EObject current = null;
        int ruleTerminalExpression_StartIndex = input.index();
        Token lv_op_2_0=null;
        Token lv_op_4_0=null;
        Token lv_op_6_0=null;
        Token lv_op_8_0=null;
        EObject this_StringLiteral_0 = null;



        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 162) ) { return current; }
            {
            int alt110=5;
            switch ( input.LA(1) ) {
            case RULE_STRING:
                {
                alt110=1;
                }
                break;
            case RULE_INTEGER:
                {
                alt110=2;
                }
                break;
            case RULE_DOUBLE:
                {
                alt110=3;
                }
                break;
            case RULE_BOOLEAN:
                {
                alt110=4;
                }
                break;
            case RULE_KEYWORD:
                {
                alt110=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 110, 0, input);

                throw nvae;
            }

            switch (alt110) {
                case 1 :
                    {
                    if ( state.backtracking==0 ) {

                      			/* */
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTerminalExpressionAccess().getStringLiteralParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_StringLiteral_0=ruleStringLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_StringLiteral_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					/* */
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTerminalExpressionAccess().getIntLiteralAction_1_0(),
                      						current);
                      				
                    }

                    }
                    {
                    {
                    lv_op_2_0=(Token)match(input,RULE_INTEGER,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_2_0, grammarAccess.getTerminalExpressionAccess().getOpINTEGERTerminalRuleCall_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTerminalExpressionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"op",
                      							lv_op_2_0,
                      							"gama.core.lang.Gaml.INTEGER");
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					/* */
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTerminalExpressionAccess().getDoubleLiteralAction_2_0(),
                      						current);
                      				
                    }

                    }
                    {
                    {
                    lv_op_4_0=(Token)match(input,RULE_DOUBLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_4_0, grammarAccess.getTerminalExpressionAccess().getOpDOUBLETerminalRuleCall_2_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTerminalExpressionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"op",
                      							lv_op_4_0,
                      							"gama.core.lang.Gaml.DOUBLE");
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					/* */
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTerminalExpressionAccess().getBooleanLiteralAction_3_0(),
                      						current);
                      				
                    }

                    }
                    {
                    {
                    lv_op_6_0=(Token)match(input,RULE_BOOLEAN,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_6_0, grammarAccess.getTerminalExpressionAccess().getOpBOOLEANTerminalRuleCall_3_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTerminalExpressionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"op",
                      							lv_op_6_0,
                      							"gama.core.lang.Gaml.BOOLEAN");
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    {
                    {
                    {
                    if ( state.backtracking==0 ) {

                      					/* */
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTerminalExpressionAccess().getReservedLiteralAction_4_0(),
                      						current);
                      				
                    }

                    }
                    {
                    {
                    lv_op_8_0=(Token)match(input,RULE_KEYWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_8_0, grammarAccess.getTerminalExpressionAccess().getOpKEYWORDTerminalRuleCall_4_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTerminalExpressionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"op",
                      							lv_op_8_0,
                      							"gama.core.lang.Gaml.KEYWORD");
                      					
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 162, ruleTerminalExpression_StartIndex); }
        }
        return current;
    }
    public final EObject entryRuleStringLiteral() throws RecognitionException {
        EObject current = null;
        int entryRuleStringLiteral_StartIndex = input.index();
        EObject iv_ruleStringLiteral = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 163) ) { return current; }
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStringLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStringLiteral=ruleStringLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStringLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 163, entryRuleStringLiteral_StartIndex); }
        }
        return current;
    }
    public final EObject ruleStringLiteral() throws RecognitionException {
        EObject current = null;
        int ruleStringLiteral_StartIndex = input.index();
        Token lv_op_0_0=null;


        	enterRule();

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 164) ) { return current; }
            {
            {
            {
            lv_op_0_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_op_0_0, grammarAccess.getStringLiteralAccess().getOpSTRINGTerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getStringLiteralRule());
              				}
              				setWithLastConsumed(
              					current,
              					"op",
              					lv_op_0_0,
              					"gama.core.lang.Gaml.STRING");
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 164, ruleStringLiteral_StartIndex); }
        }
        return current;
    }
    public final void synpred24_InternalGaml_fragment() throws RecognitionException {   
        EObject this_S_Declaration_0 = null;
        {
        {
        pushFollow(FOLLOW_2);
        this_S_Declaration_0=ruleS_Declaration();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    public final void synpred26_InternalGaml_fragment() throws RecognitionException {   
        EObject this_S_Assignment_1 = null;
        {
        {
        pushFollow(FOLLOW_2);
        this_S_Assignment_1=ruleS_Assignment();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    public final void synpred27_InternalGaml_fragment() throws RecognitionException {   
        EObject this_S_1Expr_Facets_BlockOrEnd_2 = null;
        {
        if ( state.backtracking==0 ) {

          					/* */
          				
        }
        pushFollow(FOLLOW_2);
        this_S_1Expr_Facets_BlockOrEnd_2=ruleS_1Expr_Facets_BlockOrEnd();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    public final void synpred28_InternalGaml_fragment() throws RecognitionException {   
        EObject this_S_Other_3 = null;
        {
        if ( state.backtracking==0 ) {

          					/* */
          				
        }
        pushFollow(FOLLOW_2);
        this_S_Other_3=ruleS_Other();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    public final void synpred29_InternalGaml_fragment() throws RecognitionException {   
        EObject this_S_Do_4 = null;
        {
        if ( state.backtracking==0 ) {

          					/* */
          				
        }
        pushFollow(FOLLOW_2);
        this_S_Do_4=ruleS_Do();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    public final void synpred180_InternalGaml_fragment() throws RecognitionException {   
        EObject this_Function_0 = null;
        {
        {
        pushFollow(FOLLOW_2);
        this_Function_0=ruleFunction();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    public final void synpred192_InternalGaml_fragment() throws RecognitionException {   
        {
        match(input,83,FOLLOW_2); if (state.failed) return ;

        }
    }

    public final boolean synpred192_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred192_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred29_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred29_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred26_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred26_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred24_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred24_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred180_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred180_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred27_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred27_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred28_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred28_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA19 dfa19 = new DFA19(this);
    protected DFA18 dfa18 = new DFA18(this);
    protected DFA49 dfa49 = new DFA49(this);
    protected DFA63 dfa63 = new DFA63(this);
    protected DFA98 dfa98 = new DFA98(this);
    protected DFA103 dfa103 = new DFA103(this);
    static final String dfa_1s = "\101\uffff";
    static final String dfa_2s = "\1\4\6\0\72\uffff";
    static final String dfa_3s = "\1\167\6\0\72\uffff";
    static final String dfa_4s = "\7\uffff\1\1\1\uffff\1\2\67\uffff";
    static final String dfa_5s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\72\uffff}>";
    static final String[] dfa_6s = {
            "\1\1\5\11\15\uffff\1\7\1\11\1\uffff\1\11\1\uffff\2\11\1\uffff\1\7\1\11\3\uffff\2\11\1\2\1\3\47\11\1\4\1\5\1\6\36\uffff\1\11\3\uffff\4\11",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "1041:3: ( ( ( ruleS_Declaration )=>this_S_Declaration_0= ruleS_Declaration ) | ( ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment ) | this_S_1Expr_Facets_BlockOrEnd_2= ruleS_1Expr_Facets_BlockOrEnd | this_S_Other_3= ruleS_Other | this_S_Do_4= ruleS_Do | this_S_Return_5= ruleS_Return | this_S_Solve_6= ruleS_Solve | this_S_If_7= ruleS_If | this_S_Try_8= ruleS_Try | this_S_Equations_9= ruleS_Equations ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA19_1 = input.LA(1);

                         
                        int index19_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_InternalGaml()) ) {s = 7;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index19_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA19_2 = input.LA(1);

                         
                        int index19_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_InternalGaml()) ) {s = 7;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index19_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA19_3 = input.LA(1);

                         
                        int index19_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_InternalGaml()) ) {s = 7;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index19_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA19_4 = input.LA(1);

                         
                        int index19_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_InternalGaml()) ) {s = 7;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index19_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA19_5 = input.LA(1);

                         
                        int index19_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_InternalGaml()) ) {s = 7;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index19_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA19_6 = input.LA(1);

                         
                        int index19_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_InternalGaml()) ) {s = 7;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index19_6);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 19, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_7s = "\103\uffff";
    static final String dfa_8s = "\1\4\7\uffff\2\0\3\uffff\46\0\20\uffff";
    static final String dfa_9s = "\1\167\7\uffff\2\0\3\uffff\46\0\20\uffff";
    static final String dfa_10s = "\1\uffff\1\1\71\uffff\1\5\1\6\1\7\1\10\1\4\1\2\1\11\1\3";
    static final String dfa_11s = "\10\uffff\1\0\1\1\3\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\20\uffff}>";
    static final String[] dfa_12s = {
            "\1\62\5\1\16\uffff\1\75\1\uffff\1\76\1\uffff\1\73\1\1\2\uffff\1\1\3\uffff\1\61\1\74\3\1\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\10\1\11\3\1\36\uffff\1\1\3\uffff\4\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_7 = DFA.unpackEncodedString(dfa_7s);
    static final char[] dfa_8 = DFA.unpackEncodedStringToUnsignedChars(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final short[] dfa_10 = DFA.unpackEncodedString(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[][] dfa_12 = unpackEncodedStringArray(dfa_12s);

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = dfa_7;
            this.eof = dfa_7;
            this.min = dfa_8;
            this.max = dfa_9;
            this.accept = dfa_10;
            this.special = dfa_11;
            this.transition = dfa_12;
        }
        public String getDescription() {
            return "1057:4: ( ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment ) | this_S_1Expr_Facets_BlockOrEnd_2= ruleS_1Expr_Facets_BlockOrEnd | this_S_Other_3= ruleS_Other | this_S_Do_4= ruleS_Do | this_S_Return_5= ruleS_Return | this_S_Solve_6= ruleS_Solve | this_S_If_7= ruleS_If | this_S_Try_8= ruleS_Try | this_S_Equations_9= ruleS_Equations )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA18_8 = input.LA(1);

                         
                        int index18_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred29_InternalGaml()) ) {s = 63;}

                         
                        input.seek(index18_8);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA18_9 = input.LA(1);

                         
                        int index18_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred29_InternalGaml()) ) {s = 63;}

                         
                        input.seek(index18_9);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA18_13 = input.LA(1);

                         
                        int index18_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_13);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA18_14 = input.LA(1);

                         
                        int index18_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_14);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA18_15 = input.LA(1);

                         
                        int index18_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_15);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA18_16 = input.LA(1);

                         
                        int index18_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_16);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA18_17 = input.LA(1);

                         
                        int index18_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_17);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA18_18 = input.LA(1);

                         
                        int index18_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_18);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA18_19 = input.LA(1);

                         
                        int index18_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_19);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA18_20 = input.LA(1);

                         
                        int index18_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_20);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA18_21 = input.LA(1);

                         
                        int index18_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_21);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA18_22 = input.LA(1);

                         
                        int index18_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_22);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA18_23 = input.LA(1);

                         
                        int index18_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_23);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA18_24 = input.LA(1);

                         
                        int index18_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_24);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA18_25 = input.LA(1);

                         
                        int index18_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_25);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA18_26 = input.LA(1);

                         
                        int index18_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_26);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA18_27 = input.LA(1);

                         
                        int index18_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_27);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA18_28 = input.LA(1);

                         
                        int index18_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_28);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA18_29 = input.LA(1);

                         
                        int index18_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_29);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA18_30 = input.LA(1);

                         
                        int index18_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_30);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA18_31 = input.LA(1);

                         
                        int index18_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_31);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA18_32 = input.LA(1);

                         
                        int index18_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_32);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA18_33 = input.LA(1);

                         
                        int index18_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_33);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA18_34 = input.LA(1);

                         
                        int index18_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_34);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA18_35 = input.LA(1);

                         
                        int index18_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_35);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA18_36 = input.LA(1);

                         
                        int index18_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_36);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA18_37 = input.LA(1);

                         
                        int index18_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_37);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA18_38 = input.LA(1);

                         
                        int index18_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_38);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA18_39 = input.LA(1);

                         
                        int index18_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_39);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA18_40 = input.LA(1);

                         
                        int index18_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_40);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA18_41 = input.LA(1);

                         
                        int index18_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_41);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA18_42 = input.LA(1);

                         
                        int index18_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_42);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA18_43 = input.LA(1);

                         
                        int index18_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_43);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA18_44 = input.LA(1);

                         
                        int index18_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_44);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA18_45 = input.LA(1);

                         
                        int index18_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_45);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA18_46 = input.LA(1);

                         
                        int index18_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_46);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA18_47 = input.LA(1);

                         
                        int index18_47 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_47);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA18_48 = input.LA(1);

                         
                        int index18_48 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred27_InternalGaml()) ) {s = 64;}

                         
                        input.seek(index18_48);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA18_49 = input.LA(1);

                         
                        int index18_49 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (true) ) {s = 65;}

                         
                        input.seek(index18_49);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA18_50 = input.LA(1);

                         
                        int index18_50 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalGaml()) ) {s = 1;}

                        else if ( (synpred28_InternalGaml()) ) {s = 66;}

                         
                        input.seek(index18_50);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 18, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_13s = "\61\uffff";
    static final String dfa_14s = "\1\4\56\35\2\uffff";
    static final String dfa_15s = "\1\121\56\156\2\uffff";
    static final String dfa_16s = "\57\uffff\1\1\1\2";
    static final String dfa_17s = "\61\uffff}>";
    static final String[] dfa_18s = {
            "\1\55\37\uffff\1\54\1\uffff\1\1\1\2\1\56\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\3\1\4\1\5\1\6\1\7",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "\1\57\4\uffff\1\60\113\uffff\1\57",
            "",
            ""
    };

    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final char[] dfa_14 = DFA.unpackEncodedStringToUnsignedChars(dfa_14s);
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[][] dfa_18 = unpackEncodedStringArray(dfa_18s);

    class DFA49 extends DFA {

        public DFA49(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 49;
            this.eot = dfa_13;
            this.eof = dfa_13;
            this.min = dfa_14;
            this.max = dfa_15;
            this.accept = dfa_16;
            this.special = dfa_17;
            this.transition = dfa_18;
        }
        public String getDescription() {
            return "2611:5: (lv_expr_0_1= ruleFunction | lv_expr_0_2= ruleVariableRef )";
        }
    }
    static final String dfa_19s = "\12\uffff";
    static final String dfa_20s = "\1\17\2\uffff\1\123\6\uffff";
    static final String dfa_21s = "\1\127\2\uffff\1\125\6\uffff";
    static final String dfa_22s = "\1\uffff\1\1\1\2\1\uffff\1\4\1\6\1\7\1\10\1\3\1\5";
    static final String dfa_23s = "\12\uffff}>";
    static final String[] dfa_24s = {
            "\1\1\102\uffff\1\2\1\3\1\4\1\7\1\5\1\6",
            "",
            "",
            "\1\10\1\uffff\1\11",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final char[] dfa_20 = DFA.unpackEncodedStringToUnsignedChars(dfa_20s);
    static final char[] dfa_21 = DFA.unpackEncodedStringToUnsignedChars(dfa_21s);
    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final short[][] dfa_24 = unpackEncodedStringArray(dfa_24s);

    class DFA63 extends DFA {

        public DFA63(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 63;
            this.eot = dfa_19;
            this.eof = dfa_19;
            this.min = dfa_20;
            this.max = dfa_21;
            this.accept = dfa_22;
            this.special = dfa_23;
            this.transition = dfa_24;
        }
        public String getDescription() {
            return "3531:2: (kw= '<-' | kw= '<<' | (kw= '>' kw= '>' ) | kw= '<<+' | (kw= '>' kw= '>-' ) | kw= '+<-' | kw= '<+' | kw= '>-' )";
        }
    }
    static final String dfa_25s = "\1\4\56\0\2\uffff";
    static final String dfa_26s = "\1\121\56\0\2\uffff";
    static final String dfa_27s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\2\uffff}>";
    static final String[] dfa_28s = {
            "\1\55\37\uffff\1\54\1\uffff\1\1\1\2\1\56\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\3\1\4\1\5\1\6\1\7",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };
    static final char[] dfa_25 = DFA.unpackEncodedStringToUnsignedChars(dfa_25s);
    static final char[] dfa_26 = DFA.unpackEncodedStringToUnsignedChars(dfa_26s);
    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final short[][] dfa_28 = unpackEncodedStringArray(dfa_28s);

    class DFA98 extends DFA {

        public DFA98(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 98;
            this.eot = dfa_13;
            this.eof = dfa_13;
            this.min = dfa_25;
            this.max = dfa_26;
            this.accept = dfa_16;
            this.special = dfa_27;
            this.transition = dfa_28;
        }
        public String getDescription() {
            return "5777:2: ( ( ( ruleFunction )=>this_Function_0= ruleFunction ) | this_VariableRef_1= ruleVariableRef )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA98_1 = input.LA(1);

                         
                        int index98_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA98_2 = input.LA(1);

                         
                        int index98_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA98_3 = input.LA(1);

                         
                        int index98_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA98_4 = input.LA(1);

                         
                        int index98_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA98_5 = input.LA(1);

                         
                        int index98_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA98_6 = input.LA(1);

                         
                        int index98_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA98_7 = input.LA(1);

                         
                        int index98_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA98_8 = input.LA(1);

                         
                        int index98_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA98_9 = input.LA(1);

                         
                        int index98_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA98_10 = input.LA(1);

                         
                        int index98_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_10);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA98_11 = input.LA(1);

                         
                        int index98_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_11);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA98_12 = input.LA(1);

                         
                        int index98_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_12);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA98_13 = input.LA(1);

                         
                        int index98_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_13);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA98_14 = input.LA(1);

                         
                        int index98_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_14);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA98_15 = input.LA(1);

                         
                        int index98_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_15);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA98_16 = input.LA(1);

                         
                        int index98_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_16);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA98_17 = input.LA(1);

                         
                        int index98_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_17);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA98_18 = input.LA(1);

                         
                        int index98_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_18);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA98_19 = input.LA(1);

                         
                        int index98_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_19);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA98_20 = input.LA(1);

                         
                        int index98_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_20);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA98_21 = input.LA(1);

                         
                        int index98_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_21);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA98_22 = input.LA(1);

                         
                        int index98_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_22);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA98_23 = input.LA(1);

                         
                        int index98_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_23);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA98_24 = input.LA(1);

                         
                        int index98_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_24);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA98_25 = input.LA(1);

                         
                        int index98_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_25);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA98_26 = input.LA(1);

                         
                        int index98_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_26);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA98_27 = input.LA(1);

                         
                        int index98_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_27);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA98_28 = input.LA(1);

                         
                        int index98_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_28);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA98_29 = input.LA(1);

                         
                        int index98_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_29);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA98_30 = input.LA(1);

                         
                        int index98_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_30);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA98_31 = input.LA(1);

                         
                        int index98_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_31);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA98_32 = input.LA(1);

                         
                        int index98_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_32);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA98_33 = input.LA(1);

                         
                        int index98_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_33);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA98_34 = input.LA(1);

                         
                        int index98_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_34);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA98_35 = input.LA(1);

                         
                        int index98_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_35);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA98_36 = input.LA(1);

                         
                        int index98_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_36);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA98_37 = input.LA(1);

                         
                        int index98_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_37);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA98_38 = input.LA(1);

                         
                        int index98_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_38);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA98_39 = input.LA(1);

                         
                        int index98_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_39);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA98_40 = input.LA(1);

                         
                        int index98_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_40);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA98_41 = input.LA(1);

                         
                        int index98_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_41);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA98_42 = input.LA(1);

                         
                        int index98_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_42);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA98_43 = input.LA(1);

                         
                        int index98_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_43);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA98_44 = input.LA(1);

                         
                        int index98_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_44);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA98_45 = input.LA(1);

                         
                        int index98_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_45);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA98_46 = input.LA(1);

                         
                        int index98_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred180_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index98_46);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 98, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_29s = "\2\uffff\56\1\1\uffff";
    static final String dfa_30s = "\1\4\1\uffff\56\4\1\uffff";
    static final String dfa_31s = "\1\167\1\uffff\56\171\1\uffff";
    static final String dfa_32s = "\1\uffff\1\1\56\uffff\1\2";
    static final String[] dfa_33s = {
            "\1\56\5\1\23\uffff\1\1\2\uffff\1\1\3\uffff\1\55\1\uffff\1\2\1\3\1\57\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\4\1\5\1\6\1\7\1\10\10\uffff\14\60\12\uffff\1\1\3\uffff\4\1",
            "",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            "\1\1\15\uffff\1\1\12\uffff\2\1\3\uffff\1\1\1\uffff\1\1\1\uffff\54\1\1\uffff\1\1\4\uffff\1\1\1\60\15\uffff\16\1\2\uffff\3\1",
            ""
    };
    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final char[] dfa_30 = DFA.unpackEncodedStringToUnsignedChars(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final short[][] dfa_33 = unpackEncodedStringArray(dfa_33s);

    class DFA103 extends DFA {

        public DFA103(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 103;
            this.eot = dfa_13;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_17;
            this.transition = dfa_33;
        }
        public String getDescription() {
            return "5916:2: ( ( ( (lv_exprs_0_0= ruleExpression ) ) (otherlv_1= ',' ( (lv_exprs_2_0= ruleExpression ) ) )* ) | ( ( (lv_exprs_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleParameter ) ) )* ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0xFFFFFFD1200003F0L,0x00F100000003FFFFL});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000090000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0xFFFFFFD000420010L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0xFFFFFFD000400012L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0xFFFFFFD000000010L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0xFFFFFFD000000030L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000100308010L,0x0000007FFC000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000100208010L,0x0000007FFC000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0xFFFFFFD100008010L,0x0000007FFC03FFFFL});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000100008010L,0x0000007FFC000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000101000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0xFFFFFFD1202003F0L,0x00F100000003FFFFL});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0xFFFFFFD100208010L,0x0000007FFC03FFFFL});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000120208010L,0x0000007FFC000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000004000000010L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000008000L,0x0000000000FC0000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000208010L,0x0000007FFC000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0xFFFFFFD200000010L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0xFFFFFEC200000000L,0x0000000000001FFFL});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0xFFFFFFD100000010L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0xFFFFFFFBB58003F0L,0x00F100000003FFFFL});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000002L,0x0000008000000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000002L,0x0000010000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000002L,0x0000020000000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000002L,0x0000040000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000004020000010L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000400000002L,0x0000780000080000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x0001800000000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0006000000000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0008000000000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0xFFFFFFD000000012L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0010000000000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000002L,0x0280000000000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0xFFFFFFD1200083F0L,0x01F1007FFC03FFFFL});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0xFFFFFFD1200083F0L,0x00F1007FFC03FFFFL});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000200000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000020000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0xFFFFFFD1600083F0L,0x00F1007FFC03FFFFL});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000002L,0x0000400000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0000000001080000L});

}